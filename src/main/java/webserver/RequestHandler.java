package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.model.ContentType;
import webserver.model.HttpResponse;
import webserver.model.HttpStatus;
import webserver.model.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".html"));

    private static final String COOKIE_LOGINED = "logined";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = new HttpResponse(out);

            if (request.getPath().equals("/user/create")) {
                handleUserCreate(request, response);
                return;
            }

            if (request.getPath().equals("/user/login")) {
                handleUserLogin(request, response);
                return;
            }

            if (request.getPath().equals("/user/list")) {
                handleUserList(request, response);
                return;
            }

            handleStatic(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleUserCreate(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        DataBase.addUser(user);
        response.sendFound("/index.html");
    }

    private void handleUserLogin(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        boolean logined = Optional.ofNullable(DataBase.findUserById(userId))
                .map(User::getPassword)
                .filter(p -> p.equals(password))
                .isPresent();

        response.setCookie(COOKIE_LOGINED, String.valueOf(logined));
        response.sendFound(logined ? "/index.html" : "/user/login_failed.html");
    }

    private void handleUserList(HttpRequest request, HttpResponse response) {
        if (!Boolean.TRUE.toString().equals(request.getCookie("logined"))) {
            response.sendFound("/user/login.html");
            return;
        }

        try {
            Template template = handlebars.compile("user/list");
            String listPage = template.apply(Collections.singletonMap("userList", DataBase.findAll()));
            response.sendOk(ContentType.HTML, listPage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            response.sendHeader(HttpStatus.NOT_FOUND);
        }
    }

    private void handleStatic(HttpRequest request, HttpResponse response) throws IOException {
        ContentType contentType = ContentType.fromUrlPath(request.getPath());
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + request.getPath());
            response.sendOk(contentType, body);
            return;
        } catch (URISyntaxException | NullPointerException ignored) {
        }

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
            response.sendOk(contentType, body);
        } catch (URISyntaxException | NullPointerException e) {
            response.sendHeader(HttpStatus.NOT_FOUND);
        }
    }

}
