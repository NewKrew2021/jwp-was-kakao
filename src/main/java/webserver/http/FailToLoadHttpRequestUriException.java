package webserver.http;

public class FailToLoadHttpRequestUriException extends RuntimeException {
    public FailToLoadHttpRequestUriException(String message, Throwable cause) {
        super(message, cause);
    }
}
