package exception;

public class InvalidRequestLineException extends Exception {
    private static final String INVALID_REQUEST_LINE_ERROR_MESSAGE = "잘못된 요청입니다.";
    public InvalidRequestLineException() {
        super(INVALID_REQUEST_LINE_ERROR_MESSAGE);
    }
}
