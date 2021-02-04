package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        Map<String, String> parameters = request.getBodies();
        User user = DataBase.findUserById(parameters.get("userId"));

        if (!checkValidUser(user, parameters.get("password"))) {
            response.addHeader("Set-Cookie", "logined=false");
            response.sendRedirect("/user/login_failed.html");
            return;
        }

        response.addHeader("Set-Cookie", "logined=true; Path=/");
        response.sendRedirect("/index.html");
    }

    private boolean checkValidUser(User user, String password) {
        return user != null && user.getPassword().equals(password);
    }
}
