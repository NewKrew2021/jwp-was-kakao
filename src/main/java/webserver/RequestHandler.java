package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import context.ApplicationContext;
import controller.Controller;
import exceptions.NoSuchResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestParser;
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
            HttpRequest httpRequest = new HttpRequestParser().parse(in);
            Controller controller = applicationContext.getControllerForPath(httpRequest.getPath());
            HttpResponse httpResponse = controller.handleRequest(httpRequest);
            httpResponse.response(out);

        } catch (NoSuchResource e) {
            // TODO: 404 NOT FOUND
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
