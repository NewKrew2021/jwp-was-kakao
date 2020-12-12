package controller;

import service.UserService;
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class LoginController implements Controller {

    public static final String LOGIN_COOKIE_KEY = "logined";
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    public void login(HttpRequest request, HttpResponse response) {
        boolean loginSuccess = userService.login(request.getParam("userId"), request.getParam("password"));
        response.setRedirectWithCookie(request,
                new Cookie(LOGIN_COOKIE_KEY, getCookieValue(loginSuccess), "/"),
                getRedirectPath(loginSuccess));
    }

    private String getCookieValue(boolean loginSuccess) {
        if (loginSuccess) {
            return "true";
        }
        return "false";
    }

    private String getRedirectPath(boolean loginSuccess) {
        if (loginSuccess) {
            return "/index.html";
        }
        return "/user/login_failed.html";
    }
}
