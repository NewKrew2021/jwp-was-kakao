package exceptions;

public class InvalidRequestMethodException extends RuntimeException {
    private static final String MESSAGE_FORMAT ="잘못된 request method 입니다. : ";

    public InvalidRequestMethodException(String method) {
        super(MESSAGE_FORMAT + method);
    }
}
