package controller;

import db.DataBase;
import exception.NotDefinedMethodException;
import http.*;
import model.LoginUser;
import model.User;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        LoginUser loginUser = LoginUser.of(request.getParameters());
        User user = DataBase.findUserById(loginUser.getUserId());


        String httpSessionId = request.getCookie("sessionId").getValue();
        HttpSession httpSession = httpSessionId == null ?
                HttpSessionStorage.createHttpSession() : HttpSessionStorage.getHttpSession(httpSessionId);
        response.addHeader("Set-Cookie", Cookie.of("sessionId", httpSession.getId(), "/").toString());

        if (user != null && user.validate(loginUser)) {
            httpSession.setAttribute("logined", true);
            response.sendRedirect("/index.html");
            return;
        }

        httpSession.setAttribute("logined", false);
        response.sendRedirect("/user/login_failed.html");
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        throw new NotDefinedMethodException();
    }
}
