package service.controller;

import service.db.DataBase;
import service.model.User;
import framework.request.HttpRequest;
import framework.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

import static framework.common.HttpHeaders.SET_COOKIE;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, String> parameters = request.getParameters();
        User user = DataBase.findUserById(parameters.get("userId"));

        if (!checkValidUser(user, parameters.get("password"))) {
            response.addHeader(SET_COOKIE.getHeader(), "logined=false");
            response.sendRedirect("/user/login_failed.html");
            return;
        }

        response.addHeader(SET_COOKIE.getHeader(), "logined=true; Path=/");
        response.sendRedirect("/index.html");
    }

    private boolean checkValidUser(User user, String password) {
        return user != null && user.getPassword().equals(password);
    }
}
