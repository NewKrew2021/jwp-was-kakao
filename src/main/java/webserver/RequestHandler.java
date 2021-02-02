package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import domain.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.KeyValueTokenizer;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseCSSHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLoginCookie(DataOutputStream dos, boolean value) {
        try {
            dos.writeBytes("Set-Cookie: logined=" + value + "; Path=/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isLogined(HttpRequest httpRequest){
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
            DataOutputStream dos = new DataOutputStream(out);

            String path = httpRequest.getUrl();
            String method = httpRequest.getMethod();

            if (method.equals("GET") && isStaticResources(path)) {
                path = "./static" + path;
                byte[] body = FileIoUtils.loadFileFromClasspath(path);
                responseCSSHeader(dos, body.length);
                responseBody(dos, body);
                return;
            }

            if (method.equals("GET") && path.startsWith("/user/list")) {
                if(!isLogined(httpRequest)){
                    response302Header(dos,"login.html");
                    return;
                }
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);

                Template template = handlebars.compile("user/list");
                List<User> users = new ArrayList<>(DataBase.findAll());
                byte[] body = template.apply(users).getBytes();

                response200Header(dos, body.length);
                responseBody(dos, body);
                return;
            }

            if (method.equals("GET") && path.endsWith(".html")) {
                path = "./templates" + path;
                byte[] body = FileIoUtils.loadFileFromClasspath(path);
                response200Header(dos, body.length);
                responseBody(dos, body);
                return;
            }

            if (method.equals("POST") && path.startsWith("/user/create")) {
                DataBase.addUser(new User(httpRequest.getParameters()));
                response302Header(dos, "/index.html");
                return;
            }

            if (method.equals("POST") && path.startsWith("/user/login")) {
                User user = DataBase.findUserById(httpRequest.getParameter("userId"));
                boolean isLoginSuccess = user.validatePassword(httpRequest.getParameter("password"));
                if (isLoginSuccess) {
                    response302Header(dos, "/index.html");
                } else {
                    response302Header(dos, "/user/login_failed.html");
                }
                addLoginCookie(dos, isLoginSuccess);
                return;
            }


        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
