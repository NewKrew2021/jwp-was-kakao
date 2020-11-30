package webserver.http;

public class HttpRequestParamException extends RuntimeException {
    public HttpRequestParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
