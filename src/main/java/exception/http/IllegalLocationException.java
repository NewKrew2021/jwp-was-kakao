package exception.http;

public class IllegalLocationException extends IllegalRequestException {
    public IllegalLocationException() {
        super("올바르지 않은 위치 지정입니다.");
    }
}
