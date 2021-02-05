package exception;

public class InvalidRequestException extends Exception{
    private static final String INVALID_REQUEST_LINE_ERROR_MESSAGE = "잘못된 요청입니다.";
    public InvalidRequestException() {
        super(INVALID_REQUEST_LINE_ERROR_MESSAGE);
    }
}
