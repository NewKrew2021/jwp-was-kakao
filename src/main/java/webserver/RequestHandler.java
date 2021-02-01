package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, String> requestParser;
    private Map<String, String> requestBodyParser;
    private boolean login = false;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        requestParser = new HashMap<>();
        requestBodyParser = new HashMap<>();
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);

            String line = bufferedReader.readLine();
            logger.debug(line);
            parseFirstLine(line);
            while (!(line = bufferedReader.readLine()).equals("")) {
                if (line == null)
                    return;
                String[] currentLine = line.split(": ");
                requestParser.put(currentLine[0], URLDecoder.decode(currentLine[1],"UTF-8"));
            }

            // 쿠키 처리
            if (requestParser.containsKey("Cookie") && requestParser.get("Cookie").equals("logined=true")) {
                login = true;
            }

            // 회원가입
            if (requestParser.containsKey("Content-Length") && requestParser.get("url").equals("/user/create") && requestParser.get("method").equals("POST")) {
                String body = IOUtils.readData(bufferedReader, Integer.parseInt(requestParser.get("Content-Length")));
                String[] currentLine = body.split("&|=");
                for (int i = 0; i < currentLine.length; i = i + 2) {
                    requestBodyParser.put(currentLine[i], URLDecoder.decode( currentLine[i + 1], "UTF-8" ));
                }
                User user = new User(requestBodyParser.get("userId"), requestBodyParser.get("password"), requestBodyParser.get("name"), requestBodyParser.get("email"));
                DataBase.addUser(user);
                response302Header(dos, "/index.html");
                return;
            }

            // 로그인
            if (requestParser.get("method").equals("POST") && requestParser.get("url").equals("/user/login")) {
                String body = IOUtils.readData(bufferedReader, Integer.parseInt(requestParser.get("Content-Length")));
                String[] currentLine = body.split("&|=");
                for (int i = 0; i < currentLine.length; i = i + 2) {
                    requestBodyParser.put(currentLine[i], currentLine[i + 1]);
                }
                login = DataBase.isPossibleLogin(requestBodyParser.get("userId"), requestBodyParser.get("password"));
                if (login) {
                    response302Header(dos, "/index.html");
                } else {
                    response302Header(dos, "/user/login_failed.html");
                }
                return;
            }

            //user list 구현
            if (requestParser.get("method").equals("GET") && requestParser.get("url").equals("/user/list.html")) {
                if (login && FileIoUtils.isExistFile("./templates" + requestParser.get("url"))) {
                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix("");
//                    Charset utf8 = StandardCharsets.UTF_8;
//                    loader.setCharset(utf8);
                    Handlebars handlebars = new Handlebars(loader);

                    Template template = handlebars.compile(requestParser.get("url"));
                    List<User> users = new ArrayList<>(DataBase.findAll());

                    String userListPage = template.apply(users);

                    byte[] body = userListPage.getBytes(StandardCharsets.UTF_8);
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
                if (!login) {
                    System.out.println("로그인안되어있음!!");
                    response302Header(dos, "/user/login.html");
                }
                return;
            }

            byte[] body = "Hello World".getBytes();
            if (FileIoUtils.isExistFile("./templates" + requestParser.get("url"))) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + requestParser.get("url"));
            }
            logger.debug("request pre : " + requestParser.get("url"));
            if ( FileIoUtils.isExistFile( "./static" + requestParser.get("url"))  ) {
                logger.debug("request pro : " + "./static" + requestParser.get("url"));
                body = FileIoUtils.loadFileFromClasspath("./static" + requestParser.get("url"));
                response200HeaderCss(dos, body.length);
                responseBody(dos, body);
                return;
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void parseFirstLine(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Not a RESTful request");
        }
        String[] currentLine = line.split(" ");

        requestParser.put("method", currentLine[0]);
        requestParser.put("url", currentLine[1]);
        requestParser.put("version", currentLine[2]);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Set-Cookie: logined=" + login + "; Path=/\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200HeaderCss(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type:" + requestParser.get("Accept") + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: http://localhost:8080" + url + " \r\n");
            dos.writeBytes("Set-Cookie: logined=" + login + "; Path=/\r\n");
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
