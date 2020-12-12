package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public void handleException(Socket connection) {
        try (OutputStream out = connection.getOutputStream()) {
            HttpResponse.error().write(new DataOutputStream(out));
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
        }
    }
}
