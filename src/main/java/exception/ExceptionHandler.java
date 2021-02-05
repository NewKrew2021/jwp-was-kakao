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

    private Map<Class, HttpStatus> exceptionToStatus = new HashMap<>();

    private final OutputStream outputStream;

    public ExceptionHandler(OutputStream outputStream) {
        this.outputStream = outputStream;
        exceptionToStatus.put(NoSuchFileException.class, HttpStatus.NOT_FOUND);
        exceptionToStatus.put(FileIOException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        exceptionToStatus.put(HttpRequestInputException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        exceptionToStatus.put(HttpResponseOutputException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        exceptionToStatus.put(URISyntaxException.class, HttpStatus.BAD_REQUEST);
        exceptionToStatus.put(HttpRequestFormatException.class, HttpStatus.BAD_REQUEST);
        exceptionToStatus.put(UnsupportedMethodException.class, HttpStatus.METHOD_NOT_ALLOWED);
    }

    public void handle(Exception e) {
        printStackTrace(e);
        HttpStatus exceptionStatus = exceptionToStatus.get(e.getClass());
        if(exceptionStatus == null) {
            exceptionStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        try {
            sendResponse(exceptionStatus);
        } catch (HttpResponseOutputException httpResponseOutputException) {
            httpResponseOutputException.printStackTrace();
        }
    }

    private void sendResponse(HttpStatus httpStatus) throws HttpResponseOutputException {
        HttpResponse httpResponse = new HttpResponse(outputStream);
        httpResponse.send(httpStatus);
    }

    private static void printStackTrace(Exception e) {
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            logger.debug(stackTraceElement.toString());
        }
    }
}
