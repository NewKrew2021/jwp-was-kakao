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

            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = new HttpRequest(in);
            logger.debug(httpRequest.toString());

            if (httpRequest.isEmpty()) {
                return;
            }

//            HttpResponse httpResponse = new HttpResponse(out);
//            logger.debug(httpResponse.toString());

            Controller controller = controllerMapper.assignController(httpRequest);

            HttpResponse httpResponse = controller.service(httpRequest);

            String responseString = httpResponse.toString();

            writeString(dos, responseString);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }


    }
    private void writeString(DataOutputStream dos, String str) throws IOException {
        dos.write(str.getBytes(StandardCharsets.UTF_8));
        dos.flush();
    }
}
