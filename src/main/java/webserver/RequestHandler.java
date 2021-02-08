package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.DefaultController;
import webserver.http.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

public class RequestHandler implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final List<? extends RequestMapping> requestMappings;
    private final Socket connection;

    public RequestHandler(Socket connectionSocket, List<? extends RequestMapping> requestMappings) {
        this.connection = connectionSocket;
        this.requestMappings = requestMappings;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            Controller controller = getController(request.getPath());
            controller.service(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Controller getController(String path) {
        return requestMappings.stream()
                .map(mapping -> mapping.getController(path))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(DefaultController.of());
    }
}
