package exceptions;

public class InvalidRequestException extends RuntimeException{
    private static String INVALID_REQUEST_MEESSAGE = "잘못된 요청 입니다.";

    public InvalidRequestException() {
        super(INVALID_REQUEST_MEESSAGE);
    }

}
