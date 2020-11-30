package webserver.http;

public interface Authenticator {
    void authenticate(HttpRequest httpRequest) throws AuthenticationException;
}
