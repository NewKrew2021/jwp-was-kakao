package exceptions;

public class ResponseCreateFailException extends RuntimeException{

    public static final String RESPONSE_CREATE_FAIL_MESSAGE = "응답을 생성하는데 실패하였습니다.";

    public ResponseCreateFailException() {
        super(RESPONSE_CREATE_FAIL_MESSAGE);
    }
}
