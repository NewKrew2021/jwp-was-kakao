package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.LoginUser;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.user.LoginRequest;
import webserver.user.UserRequest;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            Request request = Request.of(in);
            if (request.getMethod().equals("GET")) {
                if (request.getUri().indexOf("/css") == 0) {
                    byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + request.getUri());
                    responseCssHeader(dos, body.length);
                    responseBody(dos, body);
                }
                if (request.getUri().indexOf("/js") == 0 ||
                        request.getUri().indexOf("/fonts") == 0) {
                    byte[] body = FileIoUtils.loadFileFromClasspath("./static" + request.getUri());
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
                if (request.getUri().indexOf("/user/list") == 0) {
                    if (request.getHeader("Cookie") != null &&
                            request.getHeader("Cookie").contains("logined=true")) {


                        TemplateLoader loader = new ClassPathTemplateLoader();
                        loader.setPrefix("/templates");
                        loader.setSuffix(".html");
                        Handlebars handlebars = new Handlebars(loader);

                        Template template = handlebars.compile("user/list");
                        Collection<User> users = DataBase.findAll();
                        String profilePage = template.apply(users);
                        logger.debug("ProfilePage : {}", profilePage);

                        byte[] body = profilePage.getBytes();
                        response200Header(dos, body.length);
                        responseBody(dos, body);
                        return;
                    }
                    response302Header(dos, "/user/login.html");
                }
                if (request.getUri().indexOf("/user") == 0 ||
                        request.getUri().indexOf("/qna") == 0 ||
                        request.getUri().indexOf("/index.html") == 0 ||
                        request.getUri().indexOf("/favicon.ico") == 0) {
                    byte[] body = FileIoUtils.loadFileFromClasspath("./templates/" + request.getUri());
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
            }
            if (request.getMethod().equals("POST")) {
                if (request.getUri().indexOf("/user/create") == 0) {
                    UserRequest userRequest = UserRequest.of(request.getBody());
                    User user = userRequest.toUser();
                    DataBase.addUser(user);
                    response302Header(dos, "/index.html");
                }
                if (request.getUri().indexOf("/user/login") == 0) {
                    LoginUser loginUser = LoginRequest.of(request.getBody()).toLoginUser();
                    User user = DataBase.findUserById(loginUser.getUserId());
                    if (user != null && user.validate(loginUser)) {
                        response302HeaderWithCookie(dos, "/index.html", "logined=true");
                        return;
                    }
                    response302HeaderWithCookie(dos, "/user/login_failed.html", "logined=false");
                }
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }


    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseCssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302HeaderWithCookie(DataOutputStream dos, String location, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("Set-Cookie: " + cookie + "; path=/");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
