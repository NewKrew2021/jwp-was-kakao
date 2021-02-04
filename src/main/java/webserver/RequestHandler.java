package webserver;

import controller.ControllerMapper;
import model.Request;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

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
            Request request = Request.of(in);
            Response response = Response.of(out);
            requestMapper(request, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void requestMapper(Request request, Response response) throws IOException, URISyntaxException {
        if (ControllerMapper.get(request.getPath()) == null) {
            response.forward(request.getPath());
            return;
        }
        ControllerMapper.get(request.getPath()).service(request, response);
    }

}
