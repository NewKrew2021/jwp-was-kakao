package exception.http;

public class IllegalExtensionException extends IllegalRequestException {
    public IllegalExtensionException() {
        super("유효하지 않은 확장자입니다.");
    }
}
