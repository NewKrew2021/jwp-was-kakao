package model.user;

public class InvalidEmailException extends RuntimeException {
    private final static String MESSAGE = "이메일 주소는 1글자 이상이어야 합니다.";

    public InvalidEmailException() {
        super(MESSAGE);
    }
}
