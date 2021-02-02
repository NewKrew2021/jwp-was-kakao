package webserver;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String BASE_URL = "http://localhost:8080/index.html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            if (httpRequest.isEmpty()) {
                return;
            }
            HttpResponse httpResponse = new HttpResponse(out);

            if (httpRequest.getPath().equals("/user/create")) {
                User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
                DataBase.addUser(user);
                httpResponse.sendRedirect(BASE_URL);
                logger.debug(user.toString());
                return;
            }
            logger.debug("11111" + httpRequest.getMethod());
            logger.debug("22222" + httpRequest.getPath());
            logger.debug("33333" + httpRequest.getParameters());

            if (httpRequest.getPath().equals("/user/login")) {
                String userId = httpRequest.getParameter("userId");
                String password = httpRequest.getParameter("password");
                User user = DataBase.findUserById(userId);
                if (user == null || !user.getPassword().equals(password)) {
                    httpResponse.loginFalse();
                    return;
                }
                httpResponse.loginTrue();
                return;
            }

            httpResponse.forward(httpRequest.getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
