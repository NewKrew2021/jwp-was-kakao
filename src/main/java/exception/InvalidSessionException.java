package exception;

public class InvalidSessionException extends IllegalStateException {

    public InvalidSessionException() {
        super("만료된 세션입니다");
    }
}
