package controller;

import model.Model;
import model.user.User;
import model.user.UsersDto;
import service.UserService;
import webserver.HttpSession;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Optional;

public class UserController implements Controller {
    public static final String LOGIN_ATTRIBUTE = "logined";
    private static final String HOME_PATH = "/index.html";
    private static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
    private static final String LOGIN_PATH = "/user/login.html";
    private static final String USER_LIST_PATH = "/user/list.html";

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

    public String showUsers(HttpRequest request, HttpResponse response, Model model, HttpSession httpSession) {
        if (isLogined(httpSession)) {
            model.put("usersDto", new UsersDto(userService.findAll()));
            return USER_LIST_PATH;
        }
        response.setRedirect(LOGIN_PATH);
        return null;
    }

    private boolean isLogined(HttpSession httpSession) {
        return Optional.ofNullable((Boolean) httpSession.getAttribute(LOGIN_ATTRIBUTE)).orElse(false);
    }

    public void login(HttpRequest request, HttpResponse response, HttpSession httpSession) {
        boolean loginSuccess = userService.login(request.getParameter("userId"), request.getParameter("password"));
        httpSession.setAttribute(LOGIN_ATTRIBUTE, loginSuccess);
        response.setRedirect(getRedirectPath(loginSuccess));
    }

    private String getRedirectPath(boolean loginSuccess) {
        return loginSuccess ? HOME_PATH : LOGIN_FAILED_PATH;
    }
}
