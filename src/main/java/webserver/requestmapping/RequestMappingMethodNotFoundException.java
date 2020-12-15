package webserver.requestmapping;

public class RequestMappingMethodNotFoundException extends RuntimeException {
    private final static String MESSAGE = "메소드를 찾을 수 없습니다.";

    public RequestMappingMethodNotFoundException() {
        super(MESSAGE);
    }
}
