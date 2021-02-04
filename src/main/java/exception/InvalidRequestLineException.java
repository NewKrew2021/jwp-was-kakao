package exception;

public class InvalidRequestLineException extends RuntimeException{
    private static final String INVALID_REQUEST_LINE_MESSAGE = "유효하지 않은 형식의 Request Line 입니다.";

    public InvalidRequestLineException() {
        super(INVALID_REQUEST_LINE_MESSAGE);
    }

}
