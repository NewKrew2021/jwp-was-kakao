package webserver;

import domain.HttpRequest;
import domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
            logger.debug(httpRequest.toString());
            if (httpRequest.isEmpty()) {
                return;
            }
            HttpResponse httpResponse = new HttpResponse(out);

            Map<String, Handler> handlerMap = new HashMap<>();
            handlerMap.put("/user/create", new UserCreateHandler());
            handlerMap.put("/user/login", new UserLoginHandler());
            handlerMap.put("/user/list", new UserListHandler());

            Handler handler = handlerMap.get(httpRequest.getPath());

            if(handler == null) {
                handler = new FileHandler();
            }
            handler.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
