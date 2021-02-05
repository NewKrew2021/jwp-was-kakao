package webserver;

import application.Controller;
import domain.HttpRequest;
import domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class RequestHandler implements Runnable {
    public static final String MAIN = "/main";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;
    private final Map<String, Controller> controllers;

    public RequestHandler(Socket connectionSocket, Map<String, Controller> controllers) {
        this.connection = connectionSocket;
        this.controllers = controllers;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

            String url = httpRequest.getUrl();
            Controller controller = controllers.getOrDefault(url, controllers.get(MAIN));
            controller.service(httpRequest, httpResponse);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
