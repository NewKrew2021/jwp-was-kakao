package webserver.controller;

import db.DataBase;
import model.User;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;

import java.util.Optional;

public class UserLoginController implements Controller {
    private static final String COOKIE_LOGINED = "logined";

    @Override
    public String getPath() {
        return "/user/login";
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        boolean logined = Optional.ofNullable(DataBase.findUserById(userId))
                .map(User::getPassword)
                .filter(p -> p.equals(password))
                .isPresent();

        response.setCookie(COOKIE_LOGINED, String.valueOf(logined));
        response.sendFound(logined ? "/index.html" : "/user/login_failed.html");
    }
}
