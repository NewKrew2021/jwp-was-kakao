package webserver.controller;

import db.DataBase;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class LoginController extends AbstractController {
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        boolean login = DataBase.isPossibleLogin(httpRequest.getParameter("userId"), httpRequest.getParameter("password"));
        httpResponse.addHeader(SET_COOKIE, "logined=" + login);

        if (login) {
            httpResponse.sendRedirect("/index.html");
            return;
        }

        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
