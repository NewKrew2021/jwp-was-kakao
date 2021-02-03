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
            sendResponse(dos, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes(response.getHttpStatus() + " \r\n");
            dos.writeBytes(response.headersToString());
            dos.writeBytes("\r\n");
            if (response.getBody() != null) {
                dos.write(response.getBody(), 0, response.getBody().length);
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
