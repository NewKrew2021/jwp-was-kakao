package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        System.out.println("####################### try 진입 ######################33");
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);

            String line = reader.readLine();
            System.out.println(line);

            String[] firstTokens = line.split(" ");
            String requestUrl = firstTokens[1];

            Map<String, String> headers = new HashMap<>();
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                if ("".equals(line)) {
                    break;
                }
                String[] header = line.split(": ");
                headers.put(header[0], header[1]);
            }
            Map<String, String> map = new HashMap<>();
            if (!requestUrl.endsWith(".html")) {
                String[] urlTokens = requestUrl.split("\\?");
                if (firstTokens[0].equals("POST")) {
                    String requestBody = IOUtils.readData(reader, Integer.parseInt(headers.get("Content-Length")));
                    if (urlTokens[0].equals("/user/create")) {
                        String[] queryTokens = requestBody.split("&");
                        for (String query : queryTokens) {
                            String[] token = query.split("=");
                            map.put(token[0], token[1]);
                        }
                        User user = new User(
                                map.get("userId"),
                                map.get("password"),
                                map.get("name"),
                                map.get("email")
                        );
                        DataBase.addUser(user);
                        response302Header(dos, "/index.html");
                        return;
                    }
                    if (urlTokens[0].equals("/user/login")) {
                        String[] queryTokens = requestBody.split("&");
                        for (String query : queryTokens) {
                            String[] token = query.split("=");
                            map.put(token[0], token[1]);
                        }
                        User findUser = DataBase.findUserById(map.get("userId"));
                        if (!findUser.getPassword().equals(map.get("password"))) {
                            responseLogin(dos, "/user/login_failed.html", false);
                            return;
                        }
                        responseLogin(dos, "/index.html", true);
                        return;
                    }

                }
                if(firstTokens[0].equals("GET")) {
                    if (urlTokens[0].equals("/user/list")) {
                        boolean logined = headers.getOrDefault("Cookie","false").equals("logined=true");
                        if (logined) {
                            TemplateLoader loader = new ClassPathTemplateLoader();
                            loader.setPrefix("/templates");
                            loader.setSuffix(".html");
                            Handlebars handlebars = new Handlebars(loader);
                            Template template = handlebars.compile("user/list");
                            Map<String,List<User>> users = new HashMap();
                            users.put("users",new ArrayList<>(DataBase.findAll()));
                            String profilePage = template.apply(users);

                            response200Header(dos, profilePage.length());
                            responseBody(dos, profilePage.getBytes());
                            return;
                        }
                        response302Header(dos,"/user/login.html");
                        return;
                    }
                }
            }
//            if (!requestUrl.endsWith(".css")) {
//                byte[] body = FileIoUtils.loadFileFromClasspath("static/css/styles.css");
//                response200Header(dos, body.length);
//                responseBody(dos, body);
//                return;
//            }
            byte[] body = FileIoUtils.loadFileFromClasspath("templates" + requestUrl);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
        System.out.println("####################### try 탈출 ######################33");
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

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 REDIRECTED\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseLogin(DataOutputStream dos, String location, boolean loginResult) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("Set-Cookie: logined=" + loginResult + "; " + "Path=/" + "\r\n");
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
