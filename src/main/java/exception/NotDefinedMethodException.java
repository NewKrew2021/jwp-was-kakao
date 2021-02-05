package exception;

public class NotDefinedMethodException extends Exception {
    private static final String NOT_DEFINED_METHOD_ERROR_MESSAGE = "정의되지 않은 메소드입니다.";

    public NotDefinedMethodException() {
        super(NOT_DEFINED_METHOD_ERROR_MESSAGE);
    }
}
