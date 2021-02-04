package service;

import db.DataBase;
import db.Session;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpSession;
import model.User;
import view.View;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class UserService {
    public static HttpResponse createUser(HttpRequest request) {
        Map<String, String> bodyParsed = request.getParsedBody();
        User user = new User(
                bodyParsed.get("userId"),
                bodyParsed.get("password"),
                bodyParsed.get("name"),
                bodyParsed.get("email")
        );
        DataBase.addUser(user);
        return new HttpResponse().redirect("/index.html");
    }

    public static HttpResponse userLogin(HttpRequest request) {
        Map<String, String> bodyParsed = request.getParsedBody();
        User user = DataBase.findUserById(bodyParsed.get("userId"));

        if (user == null) {
            return new HttpResponse().redirect("/user/login_failed.html");
        }
        HttpSession session = Session.newSession();
        session.setAttribute("userId", bodyParsed.get("userId"));
        return new HttpResponse().setCookie("session=" + session.getId()).redirect("/index.html");
    }

    public static HttpResponse getUserList(HttpRequest request) throws IOException {
        byte[] body = View.getUsersView(DataBase.findAll(), "/user/list");
        return new HttpResponse().view(body);
    }

    public static HttpResponse userLogout(HttpRequest request) {
        String cookieValue = request.getCookie("session");
        if (cookieValue != null) {
            Session.invalidateSession(UUID.fromString(cookieValue));
        }
        return new HttpResponse().redirect("/index.html");
    }
}
