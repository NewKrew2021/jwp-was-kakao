package model.user;

public class InvalidNameException extends RuntimeException {
    private final static String MESSAGE = "이름은 1글자 이상이어야 합니다.";

    public InvalidNameException() {
        super(MESSAGE);
    }
}
