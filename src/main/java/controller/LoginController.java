package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import http.session.HttpSessions;
import model.User;

import java.util.UUID;

public class LoginController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (user != null && user.getPassword().equals(request.getParameter("password"))) {
            String sessionId = String.valueOf(UUID.randomUUID());

            response.setSessionId(sessionId);
            response.sendRedirect("/index.html");

            HttpSessions.add(new HttpSession(sessionId));
        }

        response.sendRedirect("/user/login_failed.html");
    }
}
