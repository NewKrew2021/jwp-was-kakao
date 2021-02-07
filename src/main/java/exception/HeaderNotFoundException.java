package exception;

public class HeaderNotFoundException extends RuntimeException {
    private static final String HEADER_NOT_FOUND_MESSAGE = "헤더를 찾을 수 없습니다.";

    public HeaderNotFoundException() {
        super(HEADER_NOT_FOUND_MESSAGE);
    }

}
