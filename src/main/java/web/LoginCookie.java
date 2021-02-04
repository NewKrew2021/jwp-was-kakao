package web;

public class LoginCookie {
    private final String loginCookie;

    private LoginCookie(String loginCookie) {
        this.loginCookie = loginCookie;
    }

    public static LoginCookie of(HttpRequest httpRequest) {
        String cookie = httpRequest.getHttpHeaders().get("Cookie");
        return new LoginCookie(cookie);
    }

    public boolean isLogined() {
        return loginCookie != null && loginCookie.equals("logined=true");
    }
}
