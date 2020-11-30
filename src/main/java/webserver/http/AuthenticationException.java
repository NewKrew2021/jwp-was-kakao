package webserver.http;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String messae, Throwable cause) {
        super(messae, cause);
    }
}
