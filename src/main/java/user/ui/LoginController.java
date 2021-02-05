package user.ui;

import db.DataBase;
import user.model.User;
import user.service.UserService;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpSession;
import webserver.domain.SessionStorage;
import webserver.ui.AbstractController;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        User user = UserService.findById(httpRequest.getParameter("userId"));
        HttpSession httpSession = new HttpSession();

        if (user == null || !user.same(httpRequest.getParameter("password"))) {
            sessionStorage.addSession(httpSession, "false");

            httpResponse.addHeader("Set-Cookie", sessionStorage.getSessionInfo(httpSession));
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }

        sessionStorage.addSession(httpSession, "true");
        httpResponse.addHeader("Set-Cookie", sessionStorage.getSessionInfo(httpSession));

        httpResponse.sendRedirect("/index.html");
    }
}
