package webserver.http.session;

public class HttpSessionStoreException extends RuntimeException {
    public HttpSessionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
