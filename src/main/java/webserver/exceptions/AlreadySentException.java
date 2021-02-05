package webserver.exceptions;

public class AlreadySentException extends RuntimeException {
    public AlreadySentException(String message) {
        super(message);
    }
}
