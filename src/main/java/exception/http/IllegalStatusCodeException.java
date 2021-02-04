package exception.http;

public class IllegalStatusCodeException extends IllegalRequestException {
    public IllegalStatusCodeException() {
        super("유효하지 않은 상태 코드입니다.");
    }
}
