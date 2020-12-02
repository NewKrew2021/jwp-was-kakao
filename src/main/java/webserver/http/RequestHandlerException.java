package webserver.http;

public class RequestHandlerException extends RuntimeException {
    public RequestHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
