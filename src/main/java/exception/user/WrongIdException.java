package exception.user;

public class WrongIdException extends UserInputException {
    public WrongIdException() {
        super("올바르지 않은 ID입니다.");
    }
}
