package webserver;

import webserver.controller.*;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String BASE_URL = "http://localhost:8080/index.html";

    private final Socket connection;
    private final ControllerMapper controllerMapper;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.controllerMapper = new ControllerMapper();
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = new HttpRequest(in);
            logger.debug(httpRequest.toString());

            if (httpRequest.isEmpty()) {
                return;
            }

            HttpResponse httpResponse = new HttpResponse(out);
            logger.debug(httpResponse.toString());

            Controller controller = controllerMapper.assignController(httpRequest);

            controller.service(httpRequest, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

//        private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
//            writeResponse(dos, httpResponse, StandardCharsets.UTF_8);
//        }
//
//        private void writeResponse(DataOutputStream dos, HttpResponse httpResponse, Charset charset) throws IOException {
//            dos.write(httpResponse.toString().getBytes(charset));
//            dos.flush();
//        }
    }
}
