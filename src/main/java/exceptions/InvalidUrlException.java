package exceptions;

public class InvalidUrlException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "url에 해당하는 controller를 찾을 수 없습니다. : ";

    public InvalidUrlException(String url) {
        super(MESSAGE_FORMAT + url);
    }
}
