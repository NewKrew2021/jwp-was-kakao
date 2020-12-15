package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public void handleException(OutputStream out) {
        HttpResponse.error().write(new DataOutputStream(out));
    }
}
