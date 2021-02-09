package exception;

import controller.RequestHandler;
import domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.OutputStream;

public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final OutputStream outputStream;

    public ExceptionHandler(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void handle(HttpException e) {
        printStackTrace(e);
        HttpStatus exceptionStatus = e.getHttpStatus();
        if(exceptionStatus == null) {
            exceptionStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        try {
            sendResponse(exceptionStatus);
        } catch (HttpException httpException) {
            httpException.printStackTrace();
        }
    }

    private void sendResponse(HttpStatus httpStatus) throws HttpException {
        HttpResponse httpResponse = new HttpResponse.Builder(httpStatus).build();
        httpResponse.send(outputStream);
    }

    private static void printStackTrace(Exception e) {
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            logger.debug(stackTraceElement.toString());
        }
    }
}
