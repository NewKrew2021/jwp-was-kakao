package webserver;

import controller.Controller;
import controller.UserController;
import controller.ViewController;
import model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private List<Controller> controllers = Arrays.asList(new UserController());

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            Controller selectedController = controllers.stream()
                    .filter(controller -> controller.hasSameBasePath(httpRequest.getPath()))
                    .findFirst()
                    .orElseGet(ViewController::new);
            selectedController.handle(httpRequest, out);
        } catch (URISyntaxException | IOException e) {
            logger.error(e.getMessage());
        }
    }
}
