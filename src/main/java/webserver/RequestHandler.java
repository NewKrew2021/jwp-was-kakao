package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }


    private boolean isLogined(HttpRequest httpRequest) {
        return httpRequest.getHeader("Cookie").equals("logined=true");
    }

    private boolean isStaticResources(String path) {
        return path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".ico") || path.endsWith(".ttf") || path.endsWith(".woff");
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
             OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

            String url = httpRequest.getUrl();
            HttpMethod method = httpRequest.getMethod();

            byte[] body;

            if (method.equals(HttpMethod.GET) && isStaticResources(url)) {
                url = "./static" + url;
                httpResponse.forward(url);
                return;
            }


            if (method.equals(HttpMethod.GET) && url.startsWith("/user/list")) {
                if (!isLogined(httpRequest)) {
                    httpResponse.sendRedirect("login.html");
                    return;
                }
                url += ".html";
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);

                Template template = handlebars.compile("user/list");
                List<User> users = new ArrayList<>(DataBase.findAll());
                body = template.apply(users).getBytes();

                httpResponse.response200Header(url, body.length);
                httpResponse.responseBody(body);
            }

            if (method.equals(HttpMethod.POST) && url.startsWith("/user/create")) {
                DataBase.addUser(new User(httpRequest.getParameters()));
                httpResponse.sendRedirect("/index.html");
                return;
            }

            if (method.equals(HttpMethod.POST) && url.startsWith("/user/login")) {
                User user = DataBase.findUserById(httpRequest.getParameter("userId"));
                // 아이디가 없습니다.
                boolean isLoginSuccess = user.validatePassword(httpRequest.getParameter("password"));
                // 패스워드가 잘못됐습니다.
                if (isLoginSuccess) {
                    httpResponse.sendRedirect("/index.html");
                    httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
                    return;
                }
                httpResponse.sendRedirect("/user/login_failed.html");
                httpResponse.addHeader("Set-Cookie", "logined=false;");
                return;
            }

            if (method.equals(HttpMethod.GET) && url.endsWith(".html")) {
                url = "./templates" + url;
                httpResponse.forward(url);
                return;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
