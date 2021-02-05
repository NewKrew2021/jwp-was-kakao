package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import request.HttpSession;
import request.HttpSessions;
import response.HttpResponse;
import webserver.WebServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import static common.HttpHeaders.SET_COOKIE;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        Map<String, String> parameters = request.getBodies();
        User user = DataBase.findUserById(parameters.get("userId"));

        if (!checkValidUser(user, parameters.get("password"))) {
            response.sendRedirect("/user/login_failed.html");
            return;
        }

        HttpSession httpSession = HttpSessions.findHttpSessionById(parameters.get(SET_COOKIE.getHeader()))
                .orElse(HttpSessions.getNewHttpSession(user.getEmail()));

        response.addHeader(SET_COOKIE.getHeader(), httpSession.getId());
        response.sendRedirect("/index.html");
    }

    private boolean checkValidUser(User user, String password) {
        return user != null && user.getPassword().equals(password);
    }
}
