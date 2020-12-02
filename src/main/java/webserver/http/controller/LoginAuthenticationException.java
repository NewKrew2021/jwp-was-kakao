package webserver.http.controller;

public class LoginAuthenticationException extends RuntimeException {
    public LoginAuthenticationException(String message) {
        super(message);
    }
}
