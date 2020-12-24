package webserver.http.exceptions;

public class HttpParamException extends RuntimeException{

    public HttpParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
