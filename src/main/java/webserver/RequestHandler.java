package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import service.LoginService;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(){}

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            logger.debug("request : {}", line);

            String[] filePath = line.split(" ");

            String[] requestPath = filePath[1].split("\\?");
            // /index.html
            // /user/create
            String path = requestPath[0];
            logger.debug("path : {}", path);
            if (path.equals("/index.html")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + filePath[1]);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if (path.equals("/user/form.html")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + filePath[1]);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if (path.equals("/user/create")) {
                Map<String, String> strBuf = new HashMap<>();
                while(!(line = br.readLine()).equals("")){
                    String[] buf = line.split(": ");
                    strBuf.put(buf[0], buf[1]);
                    logger.debug("header : {}", line);
                }
                int contentLength = Integer.parseInt(strBuf.get("Content-Length"));
                String body = IOUtils.readData(br,contentLength);
                Map<String, String> argument = parseArgument(body);
                new LoginService().createUser(argument);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            }

            if (path.equals("/user/login.html")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + filePath[1]);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if (path.equals("/user/login_failed.html")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + filePath[1]);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if (path.equals("/user/list.html")) {
                Map<String, String> strBuf = new HashMap<>();
                while(!(line = br.readLine()).equals("")){
                    String[] buf = line.split(": ");
                    strBuf.put(buf[0], buf[1]);
                    logger.debug("header : {}", line);
                }
                String loginCheck = strBuf.get("Cookie");
                if(!Boolean.parseBoolean(loginCheck.split("=")[1])){
                    // page 이동
                    DataOutputStream dos = new DataOutputStream(out);
                    response302Header(dos, "/login.html");
                }
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + filePath[1]);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if (path.equals("/user/login")){
                // post로 오는 id, password 분류
                Map<String, String> strBuf = new HashMap<>();
                while(!(line = br.readLine()).equals("")){
                    String[] buf = line.split(": ");
                    strBuf.put(buf[0], buf[1]);
                    logger.debug("header : {}", line);
                }
                int contentLength = Integer.parseInt(strBuf.get("Content-Length"));
                String body = IOUtils.readData(br,contentLength);
                Map<String, String> argument = parseArgument(body);
                String userId = argument.get("userId");
                String password = argument.get("password");
                DataOutputStream dos = new DataOutputStream(out);

                // Database에 있는 것 불러와서 비교
                Optional<User> user = DataBase.findUserById(userId);
                try {
                    user.filter(u -> u.hasPassword(password)).orElseThrow(RuntimeException::new);
                    response302HeaderWithCookie(dos, "/index.html", "true");
                } catch (RuntimeException e) {
                    response302HeaderWithCookie(dos, "/user/login_failed.html", "false");
                }

            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    protected Map<String, String> parseArgument(String argumentText) throws UnsupportedEncodingException {
        String[] argumentStr = argumentText.split("&");
        Map<String, String> argument = new HashMap<>();

        for (String s : argumentStr) {
            String[] ss = s.split("=");
            argument.put(ss[0], URLDecoder.decode(ss[1], "UTF-8"));
        }
        return argument;
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


    private void response302HeaderWithCookie(DataOutputStream dos, String url, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes(String.format("Set-Cookie: logined=%s; Path=/\r\n", cookie));
            dos.writeBytes(String.format("Location: http://localhost:8080%s \r\n", url));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes(String.format("Location: http://localhost:8080%s \r\n", url));
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
}
