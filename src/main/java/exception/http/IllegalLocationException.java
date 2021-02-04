package exception.http;

public class IllegalLocationException extends RuntimeException {
    public IllegalLocationException() {
        super("올바르지 않은 위치 지정입니다.");
    }
}
