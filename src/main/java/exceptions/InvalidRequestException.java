package exceptions;

public class InvalidRequestException extends RuntimeException{
    private static final String INVALID_REQUEST_MESSAGE = "잘못된 요청 입니다.";

    public InvalidRequestException() {
        super(INVALID_REQUEST_MESSAGE);
    }
}
