package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import utils.IOUtils;
import web.HttpRequest;
import web.HttpResponse;
import web.HttpUrl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

            if ((httpUrl.endsWith(".css") || httpUrl.endsWith(".js")) && httpRequest.hasSameMethod(HttpMethod.GET)) {
                String body = FileIoUtils.loadFileFromClasspath("./static" + httpUrl.getUrl());
                HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK);

                httpResponse.addHeader("Content-Type", httpUrl.endsWith(".css") ? "text/css" : "text/html;charset=utf-8");
                httpResponse.addHeader("Content-Length", String.valueOf(body.length()));
                httpResponse.setBody(body);

                writeResponse(dos, httpResponse);
            }
            if ((httpUrl.endsWith(".woff") || httpUrl.endsWith(".ttf")) && httpRequest.hasSameMethod(HttpMethod.GET)) {
                String body = FileIoUtils.loadFileFromClasspath("./static" + httpUrl.getUrl());
                HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK);

                httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
                httpResponse.addHeader("Content-Length", String.valueOf(body.length()));
                httpResponse.setBody(body);

                writeResponse(dos, httpResponse, StandardCharsets.ISO_8859_1);
            }

            if ((httpUrl.endsWith(".html") || httpUrl.endsWith(".ico")) && httpRequest.hasSameMethod(HttpMethod.GET)) {
                String body = FileIoUtils.loadFileFromClasspath("./templates" + httpUrl.getUrl());
                HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK);

                httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
                httpResponse.addHeader("Content-Length", String.valueOf(body.length()));
                httpResponse.setBody(body);

                writeResponse(dos, httpResponse);
            }

            if (httpUrl.hasSameUrl("/user/list") && httpRequest.hasSameMethod(HttpMethod.GET)) {
                String cookie = httpRequest.getHttpHeaders().get("Cookie");
                if (cookie == null || !cookie.equals("logined=true")) {
                    HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
                    httpResponse.addHeader("Location", "/user/login.html");

                    writeResponse(dos, httpResponse);
                } else {
                    String body = FileIoUtils.loadHandleBarFromClasspath("user/list", DataBase.findAll());
                    HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK);

                    httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
                    httpResponse.addHeader("Content-Length", String.valueOf(body.length()));
                    httpResponse.setBody(body);

                    writeResponse(dos, httpResponse);
                }
            }

            if (httpUrl.hasSameUrl("/user/login") && httpRequest.hasSameMethod(HttpMethod.POST)) {
                Map<String, String> parameters = HttpUrl.parseParameter(httpRequest.getHttpBody().getBody());
                Optional<User> user = DataBase.findUserById(parameters.get("userId"));

                if (!user.isPresent() || !user.get().getPassword().equals(parameters.get("password"))) {
                    HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
                    httpResponse.addHeader("Location", "/user/login_failed.html");
                    httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");

                    writeResponse(dos, httpResponse);
                } else {
                    HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
                    httpResponse.addHeader("Location", "/index.html");
                    httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");

                    writeResponse(dos, httpResponse);
                }
            }
            if (httpUrl.hasSameUrl("/user/create") && httpRequest.hasSameMethod(HttpMethod.POST)) {
                Map<String, String> parameters = HttpUrl.parseParameter(httpRequest.getHttpBody().getBody());
                DataBase.addUser(new User(
                        parameters.get("userId"),
                        parameters.get("password"),
                        parameters.get("name"),
                        parameters.get("email")));

                HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
                httpResponse.addHeader("Location", "/index.html");

                writeResponse(dos, httpResponse);
            }
        } catch (IOException e) {

            logger.error(e.getMessage());
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        writeResponse(dos, httpResponse, StandardCharsets.UTF_8);
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse, Charset charset) throws IOException {
        dos.write(httpResponse.toString().getBytes(charset));
        dos.flush();
    }
}
