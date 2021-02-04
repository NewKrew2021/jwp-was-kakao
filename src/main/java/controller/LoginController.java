package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.HttpSession;
import model.User;
import webserver.WebServer;

import java.util.UUID;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParams().get("userId"));
        if (user != null && user.getPassword().equals(request.getParams().get("password"))) {
            response.addHeader("Set-Cookie", "logined=true; Path=/");

            UUID sessionId = UUID.randomUUID();
            WebServer.sessions.put(sessionId.toString(), new HttpSession(sessionId.toString(), user.getUserId()));
            response.addHeader("Set-Cookie", "sessionId=" + sessionId.toString() + "; Path=/");
            response.sendRedirect("/index.html");
        }

        response.addHeader("Set-Cookie", "logined=false; Path=/");
        response.sendRedirect("/user/login_failed.html");
    }
}
