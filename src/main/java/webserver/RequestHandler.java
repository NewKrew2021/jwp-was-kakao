package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.KeyValueTokenizer;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
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

    private void addLoginCookie(DataOutputStream dos, boolean value) {
        try {
            dos.writeBytes("Set-Cookie: logined=" + value + "; Path=/");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             OutputStream out = connection.getOutputStream()) {
            Request request = new Request(br);
            DataOutputStream dos = new DataOutputStream(out);

            String path = request.getPath();
            String method = request.getMethod();


            if (method.equals("GET") && path.endsWith(".html")) {
                path = "./templates" + path;
            }

            if (method.equals("POST") && path.startsWith("/user/create")) {
                DataBase.addUser(User.of(request.getBody()));
                response302Header(dos,"/index.html");
                return;
            }

            if (method.equals("POST") && path.startsWith("/user/login")) {
                String body = request.getBody();
                Map<String, String> bodyMap = KeyValueTokenizer.of(body);
                User user = DataBase.findUserById(bodyMap.get("userId"));
                boolean isLoginSuccess = user.validatePassword(bodyMap.get("password"));
                if (isLoginSuccess) {
                    response302Header(dos, "/index.html");
                }
                else {
                    response302Header(dos, "/user/login_failed.html");
                }
                addLoginCookie(dos, isLoginSuccess);
                return;
            }

            byte[] body = FileIoUtils.loadFileFromClasspath(path);

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
