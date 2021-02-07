package user.exceptions;

public class InvalidFormInputException extends IllegalArgumentException {
    public InvalidFormInputException(String message) {
        super(message);
    }
}
