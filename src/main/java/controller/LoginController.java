package controller;

import db.DataBase;
import exception.NotDefinedMethodException;
import model.LoginUser;
import model.User;
import webserver.Request;
import webserver.Response;

public class LoginController extends AbstractController {
    @Override
    public void doPost(Request request, Response response) throws Exception {
        LoginUser loginUser = LoginUser.of(request.getParameters());
        User user = DataBase.findUserById(loginUser.getUserId());
        if (user != null && user.validate(loginUser)) {
            response.addHeader("Set-Cookie", "logined=true; path=/");
            response.sendRedirect("/index.html");
            return;
        }
        response.addHeader("Set-Cookie", "logined=false; path=/");
        response.sendRedirect("/user/login_failed.html");
    }

    @Override
    public void doGet(Request request, Response response) throws Exception {
        throw new NotDefinedMethodException();
    }
}
