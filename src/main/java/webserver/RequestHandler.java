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
import webserver.model.HttpStatus;
import webserver.model.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".html"));

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            DataOutputStream dos = new DataOutputStream(out);

            if (request.getPath().equals("/user/create")) {
                handleUserCreate(request, dos);
                return;
            }

            if (request.getPath().equals("/user/login")) {
                handleUserLogin(request, dos);
                return;
            }

            if (request.getPath().equals("/user/list")) {
                handleUserList(request, dos);
                return;
            }

            handleStatic(request, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleUserCreate(HttpRequest request, DataOutputStream dos) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        DataBase.addUser(user);
        responseHeaderOnly(dos, HttpStatus.FOUND, Collections.singletonMap("Location", "/index.html"));
    }

    private void handleUserLogin(HttpRequest request, DataOutputStream dos) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        boolean logined = Optional.ofNullable(DataBase.findUserById(userId))
                .map(User::getPassword)
                .filter(p -> p.equals(password))
                .isPresent();

        Map<String, String> headers = new HashMap<>();
        headers.put("Set-Cookie", String.format("logined=%s; Path=/", logined));
        headers.put("Location", logined ? "/index.html" : "/user/login_failed.html");
        responseHeaderOnly(dos, HttpStatus.FOUND, headers);
    }

    private void handleUserList(HttpRequest request, DataOutputStream dos) {
        if (!Boolean.TRUE.toString().equals(request.getCookie("logined"))) {
            responseHeaderOnly(dos, HttpStatus.FOUND, Collections.singletonMap("Location", "/user/login.html"));
            return;
        }

        try {
            Template template = handlebars.compile("user/list");
            String listPage = template.apply(Collections.singletonMap("userList", DataBase.findAll()));
            byte[] body = listPage.getBytes();
            response200Header(dos, body.length, ContentType.HTML);
            responseBody(dos, body);
        } catch (IOException e) {
            e.printStackTrace();
            responseHeaderOnly(dos, HttpStatus.NOT_FOUND);
        }
    }

    private void handleStatic(HttpRequest request, DataOutputStream dos) throws IOException {
        ContentType contentType = ContentType.fromUrlPath(request.getPath());
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + request.getPath());
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
            return;
        } catch (URISyntaxException | NullPointerException ignored) {
        }

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (URISyntaxException | NullPointerException e) {
            responseHeaderOnly(dos, HttpStatus.NOT_FOUND);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, ContentType contentType) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", contentType.getMimeType());
        headers.put("Content-Length", String.valueOf(lengthOfBodyContent));
        responseHeaderOnly(dos, HttpStatus.OK, headers);
    }

    private void responseHeaderOnly(DataOutputStream dos, HttpStatus status) {
        responseHeaderOnly(dos, status, Collections.emptyMap());
    }

    private void responseHeaderOnly(DataOutputStream dos, HttpStatus status, Map<String, String> headers) {
        ResponseWriter writer = new ResponseWriter(dos);
        writer.format("HTTP/1.1 %d %s", status.getCode(), status.getMessage());
        writer.println();
        headers.forEach((k, v) -> writer.println(String.join(": ", k, v)));
        writer.println();
        writer.flush();
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    static class ResponseWriter extends PrintWriter {
        ResponseWriter(OutputStream out) {
            super(out, false);
        }

        @Override
        public void println() {
            write("\r\n");
        }
    }
}
