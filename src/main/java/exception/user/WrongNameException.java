package exception.user;

public class WrongNameException extends RuntimeException {
    public WrongNameException() {
        super("올바르지 않은 이름입니다.");
    }
}
