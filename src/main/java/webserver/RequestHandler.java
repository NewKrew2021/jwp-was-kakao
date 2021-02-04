package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import utils.Dispatcher;
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
            HttpRequest httpRequest = new HttpRequestParser(IOUtils.readRequest(in)).parse();
            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse response = Dispatcher.dispatch(httpRequest);
            response.sendResponse(dos);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
