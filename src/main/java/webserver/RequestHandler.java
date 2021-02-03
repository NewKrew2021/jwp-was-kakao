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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String BASE_URL = "http://localhost:8080/index.html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            logger.debug(httpRequest.toString());
            if (httpRequest.isEmpty()) {
                return;
            }
            HttpResponse httpResponse = new HttpResponse(out);

            if (httpRequest.getPath().equals("/user/create")) {
                User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
                DataBase.addUser(user);
                httpResponse.sendRedirect(BASE_URL);
                logger.debug(user.toString());
                return;
            }

            if (httpRequest.getPath().equals("/user/login")) {
                String id = httpRequest.getParameter("userId");
                String password = httpRequest.getParameter("password");
                User user = DataBase.findUserById(id);
                if (user == null || !user.getPassword().equals(password)) {
                    httpResponse.loginFalse();
                    return;
                }
                httpResponse.loginTrue();
                return;
            }

            if(httpRequest.getPath().equals("/user/list")) {
                if (httpRequest.getCookie("logined") == null || httpRequest.getCookie("logined").equals("false")) {
                    httpResponse.forward("/user/login.html");
                    return;
                }

                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);

                Template template = handlebars.compile("user/list");

                String profilePage = template.apply(DataBase.findAll());
                logger.debug("ProfilePage : {}", profilePage);
                //TODO URLDecoder
                httpResponse.handleUserList(URLDecoder.decode(profilePage,"UTF-8"));

                return;
            }

            httpResponse.forward(httpRequest.getPath());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
