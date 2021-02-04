package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import exception.ExceptionHandler;
import webserver.URLMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

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
            ExceptionHandler.getInstance().setOutputStream(out);

            HttpRequest httpRequest = new HttpRequest(in);
            logger.debug(httpRequest.toString());
            HttpResponse httpResponse = new HttpResponse(out);

            Controller controller = URLMapper.get(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);
            logger.debug(httpResponse.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
