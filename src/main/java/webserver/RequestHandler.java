package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import utils.FileIoUtils;
import utils.IOUtils;
import web.HttpRequest;
import web.HttpUrl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;

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
            HttpRequest httpRequest = HttpRequest.of(in);
            HttpUrl httpUrl = httpRequest.getHttpUrl();
            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = "Hello World".getBytes();
            if ((httpUrl.endsWith(".css") || httpUrl.endsWith(".js") || httpUrl.endsWith(".woff") || httpUrl.endsWith(".ttf")) && httpRequest.hasSameMethod(HttpMethod.GET)) {
                body = FileIoUtils.loadFileFromClasspath("./static" + httpUrl.getUrl());
                response200Header(dos, body.length, httpUrl.endsWith(".css") ? "text/css" : "text/html;charset=utf-8");
                responseBody(dos, body);
            }
            if ((httpUrl.endsWith(".html") || httpUrl.endsWith(".ico")) && httpRequest.hasSameMethod(HttpMethod.GET)) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + httpUrl.getUrl());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if (httpUrl.hasSameUrl("/user/list") && httpRequest.hasSameMethod(HttpMethod.GET)) {
                String cookie = httpRequest.getHttpHeaders().get("Cookie");
                if (cookie == null || !cookie.equals("logined=true")) {
                    response302Header(dos, "/user/login.html");
                } else {
                    body = FileIoUtils.loadHandleBarFromClasspath("user/list", DataBase.findAll());
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
            }

            if (httpUrl.hasSameUrl("/user/login") && httpRequest.hasSameMethod(HttpMethod.POST)) {
                Map<String, String> parameters = HttpUrl.parseParameter(httpRequest.getHttpBody().getBody());
                Optional<User> user = DataBase.findUserById(parameters.get("userId"));

                if (!user.isPresent() || !user.get().getPassword().equals(parameters.get("password"))) {
                    response302Header(dos, "/user/login_failed.html");
                    responseCookieHeader(dos, "logined=false; Path=/");
                } else {
                    response302Header(dos, "/index.html");
                    responseCookieHeader(dos, "logined=true; Path=/");
                }
            }
            if (httpUrl.hasSameUrl("/user/create") && httpRequest.hasSameMethod(HttpMethod.POST)) {
                Map<String, String> parameters = HttpUrl.parseParameter(httpRequest.getHttpBody().getBody());
                DataBase.addUser(new User(
                        parameters.get("userId"),
                        parameters.get("password"),
                        parameters.get("name"),
                        parameters.get("email")));
                response302Header(dos, "/index.html");
            }
        } catch (IOException e) {

            logger.error(e.getMessage());
        }
    }

    private void responseCookieHeader(DataOutputStream dos, String cookie) {
        try {
            dos.writeBytes("Set-Cookie: " + cookie + IOUtils.NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found " + IOUtils.NEW_LINE);
            dos.writeBytes("Location: " + location + IOUtils.NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        response200Header(dos, lengthOfBodyContent, "text/html;charset=utf-8");
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String type) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK " + IOUtils.NEW_LINE);
            dos.writeBytes("Content-Type: " + type + IOUtils.NEW_LINE);
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "" + IOUtils.NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.writeBytes("" + IOUtils.NEW_LINE);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
