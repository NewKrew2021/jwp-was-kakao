package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;
import vo.UserSessionVO;
import webserver.HttpSession;
import webserver.SessionStorage;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParams().get("userId"));
        if (user != null && user.getPassword().equals(request.getParams().get("password"))) {
            UserSessionVO userSessionVO = new UserSessionVO(user);
            HttpSession session = SessionStorage.getSession();
            session.setAttribute("USER", userSessionVO);
            response.setCookie("/", session.getId());
            response.sendRedirect("/index.html");

        }
        response.sendRedirect("/user/login_failed.html");

    }
}
