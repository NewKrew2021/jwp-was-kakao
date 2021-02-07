package exception;

public class InvalidRequestException extends RuntimeException{
    private static String INVALID_REQUEST_MESSAGE = "입력란을 모두 채우지 않았습니다.";

    public InvalidRequestException() {
        super(INVALID_REQUEST_MESSAGE);
    }

}
