package model.user;

public class InvalidUserIdException extends RuntimeException {
    private final static String MESSAGE = "아이디는 1글자 이상이어야 합니다.";

    public InvalidUserIdException() {
        super(MESSAGE);
    }
}
