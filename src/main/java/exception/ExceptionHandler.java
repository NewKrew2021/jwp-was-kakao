package exception;

import controller.RequestHandler;
import domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.*;

public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static ExceptionHandler exceptionHandler;

    private OutputStream outputStream;
    private Map<Class, ExceptionFunction> exceptionToFunction;

    private ExceptionHandler() {
        exceptionToFunction = new HashMap<>();
        exceptionToFunction.put(NoSuchFileException.class, this::sendNotFound);
        exceptionToFunction.put(FileIOException.class, this::sendInternalServerError);
        exceptionToFunction.put(HttpRequestInputException.class, this::sendInternalServerError);
        exceptionToFunction.put(HttpResponseOutputException.class, this::sendInternalServerError);
        exceptionToFunction.put(URISyntaxException.class, this::sendBadRequest);
        exceptionToFunction.put(HttpRequestFormatException.class, this::sendBadRequest);
    }

    public static ExceptionHandler getInstance() {
        if(exceptionHandler == null) {
            exceptionHandler = new ExceptionHandler();
        }
        return exceptionHandler;
    }

    public void handle(Exception e) {
        printStackTrace(e);
        try {
            exceptionToFunction.get(e.getClass()).sendResponse();
        } catch (HttpResponseOutputException httpResponseOutputException) {
            httpResponseOutputException.printStackTrace();
        }
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private void sendNotFound() throws HttpResponseOutputException {
        HttpResponse httpResponse = new HttpResponse(outputStream);
        httpResponse.send(HttpStatus.NOT_FOUND);
    }

    private void sendBadRequest() throws HttpResponseOutputException {
        HttpResponse httpResponse = new HttpResponse(outputStream);
        httpResponse.send(HttpStatus.BAD_REQUEST);
    }

    private void sendInternalServerError() throws HttpResponseOutputException {
        HttpResponse httpResponse = new HttpResponse(outputStream);
        httpResponse.send(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void printStackTrace(Exception e) {
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            logger.debug(stackTraceElement.toString());
        }
    }
}
