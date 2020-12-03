package apps.slipp.authentication;

import webserver.http.*;

import java.util.Arrays;
import java.util.List;

/**
 * Cookie 기반 인증을 처리합니다.
 * <p>
 * 1. cookie 에 logined=true 가 존재하면 인증통과
 * 2. cookie 에 logined=false 이거나 값이 존재하지 않으면 인증실패 AuthenticationException 을 던진다.
 */
public class CookieAuthenticator implements HttpRequestPreProcessor, Authenticator {

    public static final String AUTHENTICATION_COOKIE_NAME = "logined";

    private List<String> matchedPaths;

    public CookieAuthenticator(String... matchedPaths) {
        this.matchedPaths = Arrays.asList(matchedPaths);
    }

    @Override
    public boolean matches(HttpRequest httpRequest) {
        return matchedPaths.contains(httpRequest.getPath());
    }

    @Override
    public void apply(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            authenticate(httpRequest);
        } catch (AuthenticationException e) {
            throw new CookieAuthenticationException("Cookie 인증정보가 유효하지 않습니다.", e);
        }
    }

    @Override
    public void authenticate(HttpRequest httpRequest) throws AuthenticationException {
        if (!isLogined(httpRequest))
            throw new AuthenticationException("해당 요청은 인증이 필요합니다. ( path: " + httpRequest.getPath() + " )");
    }

    private boolean isLogined(HttpRequest httpRequest) {
        String logined = httpRequest.getCookie(AUTHENTICATION_COOKIE_NAME);
        if (logined == null) return false;
        return Boolean.valueOf(logined);
    }
}
