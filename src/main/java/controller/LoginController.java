package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;
import session.HttpSession;
import session.HttpSessions;

import java.util.UUID;

public class LoginController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParams().get("userId"));
        if (user != null && user.getPassword().equals(request.getParams().get("password"))) {
            String sessionId = String.valueOf(UUID.randomUUID());

            response.addHeader("Set-Cookie", "SessionId=" + sessionId + "; Path=/");
            response.sendRedirect("/index.html");

            HttpSessions.add(new HttpSession(sessionId));
        }

        response.sendRedirect("/user/login_failed.html");
    }
}
