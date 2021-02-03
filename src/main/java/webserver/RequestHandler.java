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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            String request= IOUtils.buildString(in);
            HttpRequest httpRequest = HttpRequestParser.getRequest(request);
            DataOutputStream dos = new DataOutputStream(out);

            Optional<Handler> handler = HandlerMapper.findHandler(httpRequest);
            if(handler.isPresent()) {
                HttpResponse response = handler.get().handleRequest(httpRequest);
                sendResponse(dos, response);
            } else {
                // TODO: 2021/02/03 NOT FOUND
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes(response.getHttpStatus() + " \r\n");
            dos.writeBytes(response.headersToString());
            dos.writeBytes("\r\n");
            if(response.getBody() != null) {
                dos.write(response.getBody(), 0, response.getBody().length);
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
