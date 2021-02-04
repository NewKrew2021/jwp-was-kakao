package service;

import db.DataBase;
import db.Session;
import model.*;
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
        return new HttpResponse().redirect(Constant.INDEX_HTML);
    }

    public static HttpResponse userLogin(HttpRequest request) {
        Map<String, String> bodyParsed = request.getParsedBody();
        User user = DataBase.findUserById(bodyParsed.get("userId"));

        if (user == null) {
            return new HttpResponse().redirect(Constant.USER_LOGIN_FAILED_HTML);
        }
        HttpSession session = Session.newSession();
        session.setAttribute("userId", bodyParsed.get("userId"));
        return new HttpResponse().setCookie(Constant.SESSION_NAME + "=" + session.getId()).redirect(Constant.INDEX_HTML);
    }

    public static HttpResponse getUserList() throws IOException {
        byte[] body = View.getUsersView(DataBase.findAll(), Constant.USER_LIST_URI);
        return new HttpResponse().view(body);
    }

    public static HttpResponse userLogout(HttpRequest request) {
        String cookieValue = request.getCookie(Constant.SESSION_NAME);
        if (cookieValue != null) {
            Session.invalidateSession(UUID.fromString(cookieValue));
        }
        return new HttpResponse().redirect(Constant.INDEX_HTML);
    }
}
