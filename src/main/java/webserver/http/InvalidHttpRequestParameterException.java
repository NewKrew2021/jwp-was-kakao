package webserver.http;

public class InvalidHttpRequestParameterException extends RuntimeException {
    public InvalidHttpRequestParameterException(String message) {
        super(message);
    }
}
