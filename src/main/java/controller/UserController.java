package controller;

import model.Model;
import model.user.User;
import model.user.UsersDto;
import service.UserService;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import static controller.LoginController.LOGIN_COOKIE_KEY;

public class UserController implements Controller {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUser(HttpRequest request, HttpResponse response) {
        userService.addUser(new User(request.getParam("userId"), request.getParam("password"), request.getParam("name"), request.getParam("email")));
        response.setRedirect(request, "/index.html");
    }

    public String showUsers(HttpRequest request, HttpResponse response, Model model) {
        if (isLogined(request)) {
            model.put("usersDto", new UsersDto(userService.findAll()));
            return "templates/user/list.html";
        }
        response.setRedirect(request, "/user/login.html");
        return null;
    }

    private boolean isLogined(HttpRequest request) {
        return request.getHeader()
                .getCookies()
                .stream()
                .anyMatch(cookie -> cookie.getKey().equals(LOGIN_COOKIE_KEY) && cookie.getValue().equals("true"));
    }
}
