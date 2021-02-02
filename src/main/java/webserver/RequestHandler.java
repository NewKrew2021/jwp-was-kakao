package webserver;

import controller.Controller;
import controller.Controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Controllers controllers = new Controllers();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            Controller controller = controllers.getController(httpRequest.getUri());
            controller.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
