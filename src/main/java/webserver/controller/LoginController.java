package webserver.controller;

import db.DataBase;
import db.HttpSessions;
import model.User;
import org.checkerframework.checker.units.qual.C;
import webserver.domain.*;

import java.util.UUID;

public class LoginController extends AbstractController {
    @Override
    public HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        User findUser = DataBase.findUserById(httpRequest.getParameter("userId"));
        if (wrongUser(httpRequest, findUser)) {
            Cookie cookie = new Cookie("logined=false");
            cookie.setPath("/");
            return new HttpResponse.Builder()
                    .status(HttpStatusCode.FOUND)
                    .redirect("/user/login_failed.html")
                    .cookie(cookie)
                    .build();
        }
        Cookie cookie = new Cookie("logined=true");
        cookie.setPath("/");
        Session session = new Session();
        session.addAttribute("user", findUser);
        String uuid = HttpSessions.getId();
        HttpSessions.addSession(uuid, session);
        Cookie sessionCookie = new Cookie("Session=" + uuid);
        sessionCookie.setPath("/");
        return new HttpResponse.Builder()
                .status(HttpStatusCode.FOUND)
                .redirect("/index.html")
                .cookie(cookie)
                .cookie(sessionCookie)
                .build();
    }

    private boolean wrongUser(HttpRequest httpRequest, User findUser) {
        return findUser == null || !findUser.isValidPassword(httpRequest.getParameter("password"));
    }
}
