package exception.user;

public class UserInputException extends RuntimeException {
    UserInputException(String msg) {
        super(msg);
    }
}
