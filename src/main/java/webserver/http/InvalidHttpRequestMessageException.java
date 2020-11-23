package webserver.http;

public class InvalidHttpRequestMessageException extends RuntimeException {
    public InvalidHttpRequestMessageException(String message) {
        super(message);
    }
}
