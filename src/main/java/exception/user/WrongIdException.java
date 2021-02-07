package exception.user;

public class WrongIdException extends RuntimeException {
    public WrongIdException() {
        super("올바르지 않은 ID입니다.");
    }
}
