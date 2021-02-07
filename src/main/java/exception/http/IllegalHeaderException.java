package exception.http;

public class IllegalHeaderException extends RuntimeException {
    public IllegalHeaderException() {
        super("유효하지 않은 헤더입니다.");
    }
}
