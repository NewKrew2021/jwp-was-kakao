package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.HttpController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ResponseStatus;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private static final String STATUS_PREFIX = "HTTP/1.1";

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(bufferedReader);

            RequestMappingHandler requestMappingHandler = new RequestMappingHandler();

            HttpController httpController = RequestMappingHandler.getController(httpRequest);

            HttpResponse httpResponse = httpController.response(httpRequest);
            DataOutputStream dos = new DataOutputStream(out);
            responseBody(dos, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String makeStatus(ResponseStatus httpStatus) {
        return String.format("%s %s %s \r\n", STATUS_PREFIX, httpStatus.getCode(), httpStatus.getText());
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(makeStatus(httpResponse.getStatus()));
            List<String> headers = httpResponse.getHeader().makeHeader();
            for (String header : headers) {
                dos.writeBytes(header);
            }
            dos.writeBytes("\r\n");
            dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
