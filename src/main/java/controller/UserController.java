package controller;

import model.Model;
import model.user.User;
import model.user.UsersDto;
import service.UserService;
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import static utils.template.TemplateUtils.TEMPLATE_PREFIX;

public class UserController implements Controller {
    public static final String LOGIN_COOKIE_NAME = "logined";
    private static final String HOME_PATH = "/index.html";
    private static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
    private static final String LOGIN_PATH = "/user/login.html";
    private static final String USER_LIST_PATH = TEMPLATE_PREFIX + "/user/list.html";

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUser(HttpRequest request, HttpResponse response) {
        userService.addUser(getUserFromRequest(request));
        response.setRedirect(HOME_PATH);
    }

    private User getUserFromRequest(HttpRequest request) {
        return new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
    }

    public String showUsers(HttpRequest request, HttpResponse response, Model model) {
        if (isLogined(request)) {
            model.put("usersDto", new UsersDto(userService.findAll()));
            return USER_LIST_PATH;
        }
        response.setRedirect(LOGIN_PATH);
        return null;
    }

    private boolean isLogined(HttpRequest request) {
        return request.getHeader()
                .getCookies()
                .stream()
                .anyMatch(cookie -> cookie.getName().equals(LOGIN_COOKIE_NAME) && cookie.getValue().equals("true"));
    }

    public void login(HttpRequest request, HttpResponse response) {
        boolean loginSuccess = userService.login(request.getParameter("userId"), request.getParameter("password"));
        response.setRedirectWithCookie(new Cookie(LOGIN_COOKIE_NAME, getCookieValue(loginSuccess), "/"),
                getRedirectPath(loginSuccess));
    }

    private String getCookieValue(Boolean loginSuccess) {
        return loginSuccess.toString();
    }

    private String getRedirectPath(boolean loginSuccess) {
        return loginSuccess ? HOME_PATH : LOGIN_FAILED_PATH;
    }
}
