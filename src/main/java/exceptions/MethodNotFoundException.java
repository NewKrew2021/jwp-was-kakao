package exceptions;

public class MethodNotFoundException extends RuntimeException {
    private static final String METHOD_NOT_FOUND_MESSAGE = "요청 메소드를 찾을 수 없습니다.";

    public MethodNotFoundException() {
        super(METHOD_NOT_FOUND_MESSAGE);
    }
}
