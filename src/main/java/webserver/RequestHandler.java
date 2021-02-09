package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.*;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Map<String, Controller> controllers = new HashMap<>();
    public static final String DEFAULT_CONTROLLER = "defaultController";

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/logout", new LogoutController());
        controllers.put("defaultController", new DefaultController());
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            control(in, out);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void control(InputStream in, OutputStream out) throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(in);
        String path = httpRequest.getPath();
        if (controllers.containsKey(path)) {
            controllers.get(path).service(httpRequest).sendResponse(out);
            return;
        }
        controllers.get(DEFAULT_CONTROLLER).service(httpRequest).sendResponse(out);
    }
}