package webserver.exceptions;

public class RequiredHeaderNotFoundException extends RuntimeException {
    public RequiredHeaderNotFoundException(String message) {
        super(message);
    }
}
