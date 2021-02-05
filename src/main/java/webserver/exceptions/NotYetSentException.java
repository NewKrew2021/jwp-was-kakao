package webserver.exceptions;

public class NotYetSentException extends RuntimeException {
    public NotYetSentException(String message) {
        super(message);
    }
}
