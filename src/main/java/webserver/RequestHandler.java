package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Optional;

import controller.Handler;
import controller.HandlerMapper;
import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            String request = IOUtils.buildString(in);
            HttpRequest httpRequest = HttpRequestParser.getRequest(request);
            DataOutputStream dos = new DataOutputStream(out);

            Optional<Handler> handler = HandlerMapper.findHandler(httpRequest);
            if (handler.isPresent()) {
                HttpResponse response = handler.get().handleRequest(httpRequest);
                response.sendResponse(dos);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

}
