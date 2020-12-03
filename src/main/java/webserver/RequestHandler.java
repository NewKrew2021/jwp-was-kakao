package webserver;

import dto.RequestValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private URIFactory uriFactory;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.uriFactory = new URIFactory();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                     connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestValue requestValue = RequestValue.of(in);
            requestValue.printRequest();

            Request request = Request.of(requestValue);
            Response response = uriFactory.create(request);

            response.output(new DataOutputStream(out));

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
