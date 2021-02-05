package exception;

public class InvalidPatternException extends Exception {
    private static final String INVALID_Pattern_ERROR_MESSAGE = "잘못된 패턴입니다.";

    public InvalidPatternException() {
        super(INVALID_Pattern_ERROR_MESSAGE);
    }
}
