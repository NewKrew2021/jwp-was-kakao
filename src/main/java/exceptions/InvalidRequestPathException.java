package exceptions;

public class InvalidRequestPathException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "잘못된 request path 입니다. : ";

    public InvalidRequestPathException(String path) {
        super(MESSAGE_FORMAT + path);
    }
}
