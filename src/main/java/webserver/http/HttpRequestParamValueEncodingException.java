package webserver.http;

public class HttpRequestParamValueEncodingException extends RuntimeException {
    public HttpRequestParamValueEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
