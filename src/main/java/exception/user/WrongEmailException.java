package exception.user;

public class WrongEmailException extends UserInputException {
    public WrongEmailException() {
        super("올바르지 않은 이메일입니다.");
    }
}
