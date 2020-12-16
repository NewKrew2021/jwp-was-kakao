package model.user;

public class InvalidPasswordException extends RuntimeException {
    private final static String MESSAGE = "비밀번호는 1글자 이상이어야 합니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
