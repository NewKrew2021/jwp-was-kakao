package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

            String path = httpRequest.getPath();
            HttpMethod method = httpRequest.getMethod();

            if (method == HttpMethod.POST) {
                Map<String, String> map = new HashMap<>();
                if (path.equals("/user/create")) {
                    String body = httpRequest.getBody();
                    String[] queryTokens = body.split("&");
                    for (String query : queryTokens) {
                        String[] token = query.split("=");
                        map.put(token[0], token[1]);
                    }
                    User user = new User(
                            map.get("userId"),
                            map.get("password"),
                            map.get("name"),
                            map.get("email")
                    );
                    DataBase.addUser(user);
                    httpResponse.sendRedirect("/index.html");
                    return;
                }
                if (path.equals("/user/login")) {
                    String body = httpRequest.getBody();
                    String[] queryTokens = body.split("&");
                    for (String query : queryTokens) {
                        String[] token = query.split("=");
                        map.put(token[0], token[1]);
                    }
                    User findUser = DataBase.findUserById(map.get("userId"));
                    if (!findUser.getPassword().equals(map.get("password"))) {
                        httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
                        httpResponse.sendRedirect("/user/login_failed.html");
                        return;
                    }
                    httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
                    httpResponse.sendRedirect("/index.html");
                    return;
                }

            }
            if (method == HttpMethod.GET) {
                if (path.equals("/user/list")) {
                    boolean logined = httpRequest.getHeader("Cookie").equals("logined=true");
                    if (logined) {
                        TemplateLoader loader = new ClassPathTemplateLoader();
                        loader.setPrefix("/templates");
                        loader.setSuffix(".html");
                        Handlebars handlebars = new Handlebars(loader);
                        Template template = handlebars.compile("user/list");
                        Map<String, List<User>> users = new HashMap();
                        users.put("users", new ArrayList<>(DataBase.findAll()));
                        String profilePage = template.apply(users);

                        httpResponse.forwardBody(profilePage);
                        return;
                    }
                    httpResponse.sendRedirect("/user/login.html");
                    return;
                }
            }
            httpResponse.forward(path);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
