package exception.user;

public class WrongPasswordException extends UserInputException {
    public WrongPasswordException() {
        super("올바르지 않은 비밀번호입니다.");
    }
}
