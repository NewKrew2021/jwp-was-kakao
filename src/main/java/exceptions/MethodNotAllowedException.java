package exceptions;

public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException() {
        super("허용되지 않는 메소드 타입입니다.");
    }
}
