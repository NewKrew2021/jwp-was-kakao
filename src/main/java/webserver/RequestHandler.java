package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Optional;

import controller.*;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestParser;
import webserver.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ControllerMapper controllerMapper = new ControllerMapper();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            String request = IOUtils.buildString(in);
            System.out.println(request);
            HttpRequest httpRequest = HttpRequestParser.getRequest(request);
            DataOutputStream dos = new DataOutputStream(out);

            Optional<Controller> controller = controllerMapper.findController(httpRequest);

            HttpResponse response;
            if (controller.isPresent()) {
                response = controller.get().handleRequest(httpRequest);
            } else {
                response = new HttpResponse.Builder()
                        .status("HTTP/1.1 404 Not Found")
                        .build();
            }
            response.sendResponse(dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
