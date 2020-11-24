package webserver.http;

public class FailToCreateHttpRequestException extends RuntimeException {
    public FailToCreateHttpRequestException(String message, Throwable cause) {
        super(message,cause);
    }
}
