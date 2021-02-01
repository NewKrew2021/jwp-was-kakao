package webserver;

import java.io.*;
import java.net.Socket;

import controller.Controllers;
import http.HttpRequest;
import http.HttpRequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private Controllers controllers = new Controllers();
    private HttpRequestParser parser = new HttpRequestParser();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            String request= IOUtils.buildString(in);
            parser.parse(request);
            HttpRequest httpRequest = new HttpRequest(parser.getRequestMethod(), parser.getUri(), parser.getRequestHeaders(), parser.getBody());

            //Controller 찾고
            DataOutputStream dos = new DataOutputStream(out);
            controllers.dispatch(httpRequest, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
