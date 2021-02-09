package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.controller.Controller;
import webserver.controller.ControllerMapper;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String BASE_URL = "http://localhost:8080/index.html";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest request = new HttpRequest(in);
            logger.debug(request.toString());

            if (request.isEmpty()) {
                return;
            }

            ControllerMapper controllerMapper = ControllerMapper.getInstance();
            Controller controller = controllerMapper.assignController(request);

            HttpResponse response = controller.service(request);
            logger.debug(response.toString());

            writeResponse(dos, response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse response) throws IOException {
        IOUtils.writeString(dos, response.toString());
    }
}
