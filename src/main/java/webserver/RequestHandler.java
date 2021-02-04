package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public synchronized void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = HttpResponse.of(dos);


            if (httpRequest.getMethod().equals(HttpMethod.GET)) {
                if (httpRequest.getPath().equals("/user/list")) {
                    String cookie = httpRequest.getHeaders().get("Cookie");
                    if (cookie == null || !cookie.contains("logined=true")) {
                        httpResponse.sendRedirect("/user/login.html");
                        return;
                    }

                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");
                    Handlebars handlebars = new Handlebars(loader);

                    Template template = handlebars.compile("user/list");

                    Map<String, List> parameters = new HashMap<>();
                    List<User> users = new ArrayList<>(DataBase.findAll());
                    parameters.put("users", users);

                    String profilePage = template.apply(parameters);

                    httpResponse.responseBody(profilePage.getBytes());
                    return;
                }
            }

            if (httpRequest.getMethod().equals(HttpMethod.POST)) {

                if (httpRequest.getPath().equals("/user/create")) {
                    User user = User.mapOf(httpRequest.getBodies());
                    DataBase.addUser(user);
                    httpResponse.sendRedirect("/index.html");
                    return;
                }

                if (httpRequest.getPath().equals("/user/login")) {
                    Map<String, String> parameters = httpRequest.getBodies();
                    User user = DataBase.findUserById(parameters.get("userId"));
                    if (user == null || !user.getPassword().equals(parameters.get("password"))) {
                        httpResponse.addHeader("Set-Cookie", "logined=false");
                        httpResponse.sendRedirect("/user/login_failed.html");
                    } else {
                        httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
                        httpResponse.sendRedirect("/index.html");
                    }
                    return;
                }
            }

            httpResponse.forward(httpRequest.getPath());

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

}
