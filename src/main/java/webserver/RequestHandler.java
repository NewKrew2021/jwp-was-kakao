package webserver;

import java.io.*;
import java.net.Socket;

import context.ApplicationContext;
import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ApplicationContext applicationContext;

    public RequestHandler(Socket connectionSocket, ApplicationContext applicationContext) {
        this.connection = connectionSocket;
        this.applicationContext = applicationContext;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.Parser.parse(in);
            Controller controller = applicationContext.getControllerForPath(httpRequest.getPath());
            HttpResponse httpResponse = controller.handleRequest(httpRequest);
            httpResponse.response(out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
