package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Optional;

import controller.*;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestParser;
import webserver.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private DispatcherServlet servlet = new DispatcherServlet();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            String request = IOUtils.buildString(in);
            HttpRequest httpRequest = HttpRequestParser.getRequest(request);
            HttpResponse httpResponse = new HttpResponse();

            servlet.doService(httpRequest, httpResponse);

            DataOutputStream dos = new DataOutputStream(out);
            httpResponse.sendResponse(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
