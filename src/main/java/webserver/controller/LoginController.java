package webserver.controller;

import db.DataBase;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.controller.AbstractController;

public class LoginController extends AbstractController {
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doPost(httpRequest, httpResponse);

        boolean login = DataBase.isPossibleLogin(httpRequest.getParameter("userId"), httpRequest.getParameter("password"));
        httpResponse.addHeader("Set-Cookie", "logined=" + login);

        if (login) {
            httpResponse.sendRedirect("/index.html");
            return;
        }

        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
