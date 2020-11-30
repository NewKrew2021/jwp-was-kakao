package webserver.http.authentication;

import webserver.http.AuthenticationException;

public class CookieAuthenticationException extends AuthenticationException {
    public CookieAuthenticationException(String messae, Throwable cause) {
        super(messae, cause);
    }
}
