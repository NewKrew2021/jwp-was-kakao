package exceptions;

public class InvalidResponseStatusCodeException extends RuntimeException{
    private static final String INVALID_RESPONSE_STATUS_CODE_MESSAGE = "유효 하지 않은 응답 코드 입니다";

    public InvalidResponseStatusCodeException(){
        super(INVALID_RESPONSE_STATUS_CODE_MESSAGE);
    }
}
