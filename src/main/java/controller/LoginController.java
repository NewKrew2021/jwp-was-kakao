package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;

public class LoginController extends AbstractController{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParams().get("userId"));
        if (user != null && user.getPassword().equals(request.getParams().get("password"))) {
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.sendRedirect("/index.html");
        }

        response.addHeader("Set-Cookie", "logined=false; Path=/");
        response.sendRedirect("/user/login_failed.html");
    }
}
