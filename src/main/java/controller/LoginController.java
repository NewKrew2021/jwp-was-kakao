package controller;

import db.DataBase;
import model.LoginUser;
import model.User;
import webserver.Request;
import webserver.Response;

public class LoginController extends AbstractController {
    @Override
    public void service(Request request, Response response) throws Exception {
        if (request.getMethod().equals("GET")) {
            doGet(request, response);
        }
        if (request.getMethod().equals("POST")) {
            doPost(request, response);
        }
    }

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
}
