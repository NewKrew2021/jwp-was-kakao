package webserver;

import controller.*;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Map<String, Controller> controllers = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initalizeController();
    }

    private void initalizeController() {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list.html", new ListUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("default", new DefaultController());
    }

    private Controller getController(String uri) {
        Controller controller = controllers.get(uri);
        return controller != null ? controller : controllers.get("default");
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.of(in);
            HttpResponse response = HttpResponse.of(out);

            Controller controller = getController(request.getUri());
            controller.service(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
