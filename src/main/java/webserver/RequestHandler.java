package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    static final String HTTP_VERSION_NAME = "HTTP/1.1";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(in);
            httpRequest.logRequestHeader();

            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(dos);

            ControllerMapper controllerMapper = ControllerMapper.getInstance();
            Controller controller = controllerMapper.getController(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);

            httpResponse.writeResponse();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
