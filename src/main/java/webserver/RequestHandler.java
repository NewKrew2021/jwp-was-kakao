package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.parser.HttpRequestParser;

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

            HttpRequest request = HttpRequestParser.fromInputStream(in);
            HttpResponse response = new HttpResponse(out);
            ResponseHandler.handle(request, response);

        } catch (IOException e) {
            logger.error("IOException occur!");
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
