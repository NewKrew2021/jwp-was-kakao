package exception.http;

public class CannotHandleException extends IllegalRequestException {
    public CannotHandleException() {
        super("처리할 수 없는 요청입니다.");
    }
}
