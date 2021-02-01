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
import org.springframework.http.HttpMethod;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private HttpRequest httpRequest;
    private Socket connection;
    private boolean login = false;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {


        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            this.httpRequest = new HttpRequest(in);
            DataOutputStream dos = new DataOutputStream(out);

            // 쿠키 처리
            if (httpRequest.getHeader("Cookie").equals("logined=true")) {
                login = true;
            }

            // 회원가입
            if (httpRequest.getPath().equals("/user/create") && httpRequest.getMethod().equals(HttpMethod.POST)) {

                User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
                DataBase.addUser(user);
                response302Header(dos, "/index.html");
                return;
            }

            // 로그인
            if (httpRequest.getMethod().equals(HttpMethod.POST) && httpRequest.getPath().equals("/user/login")) {
                login = DataBase.isPossibleLogin(httpRequest.getParameter("userId"), httpRequest.getParameter("password"));
                if (login) {
                    response302Header(dos, "/index.html");
                } else {
                    response302Header(dos, "/user/login_failed.html");
                }
                return;
            }

            //user list 구현
            if (httpRequest.getMethod().equals(HttpMethod.GET) && httpRequest.getPath().equals("/user/list.html")) {
                if (login && FileIoUtils.isExistFile("./templates" + httpRequest.getPath())) {
                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix("");
                    Handlebars handlebars = new Handlebars(loader);

                    Template template = handlebars.compile(httpRequest.getPath());
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
            if (FileIoUtils.isExistFile("./templates" + httpRequest.getPath())) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getPath());
            }
            logger.debug("request pre : " + httpRequest.getPath());
            if ( FileIoUtils.isExistFile( "./static" + httpRequest.getPath())  ) {
                logger.debug("request pro : " + "./static" + httpRequest.getPath());
                body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getPath());
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
            dos.writeBytes("Content-Type:" + httpRequest.getHeader("Accept") + "\r\n");
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
