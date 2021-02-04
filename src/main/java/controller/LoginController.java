package controller;

import db.DataBase;
import exception.NotDefinedMethodException;
import model.LoginUser;
import model.User;
import webserver.*;

public class LoginController extends AbstractController {

    @Override
    public void doGet(Request request, Response response) throws Exception {
        throw new NotDefinedMethodException();
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        LoginUser loginUser = LoginUser.of(request.getParameters());
        User user = DataBase.findUserById(loginUser.getUserId());
        Session session = request.getSession() == null ? SessionStorage.createSession() : request.getSession();
        response.addCookie(new Cookie("sessionId", session.getId()));
        if (user != null && user.validate(loginUser)) {
            session.setAttributes("logined", true);
            response.sendRedirect("/index.html");
            return;
        }
        session.setAttributes("logined", false);
        response.sendRedirect("/user/login_failed.html");
    }
}
