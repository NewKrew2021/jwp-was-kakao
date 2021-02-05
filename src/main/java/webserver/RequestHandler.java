package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.Controllers;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.SessionStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Controllers controllers;

    public RequestHandler(Socket connectionSocket, Controllers controllers) {
        this.connection = connectionSocket;
        this.controllers = controllers;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            controllers.service(new HttpRequest(in), new HttpResponse(out));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
