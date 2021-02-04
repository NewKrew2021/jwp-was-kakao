package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;
import webserver.HttpServletRequest;
import webserver.HttpSession;

public class LoginController extends AbstractController{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParams().get("userId"));
        if (user != null && user.getPassword().equals(request.getParams().get("password"))) {
            HttpSession session = HttpServletRequest.getSession();
//            response.addHeader("Set-Cookie", session.getId() + "; Path=/");
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.sendRedirect("/index.html");
        }

        response.addHeader("Set-Cookie", "logined=false; Path=/");
        response.sendRedirect("/user/login_failed.html");
    }
}
