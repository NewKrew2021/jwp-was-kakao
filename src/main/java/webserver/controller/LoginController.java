package webserver.controller;

import db.DataBase;
import model.User;
import webserver.domain.HttpHeader;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        User findUser = DataBase.findUserById(httpRequest.getParameter("userId"));
        if (findUser == null || !findUser.isValidPassword(httpRequest.getParameter("password"))) {
            httpResponse.addHeader(HttpHeader.SET_COOKIE, "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        httpResponse.addHeader(HttpHeader.SET_COOKIE, "logined=true; Path=/");
        httpResponse.sendRedirect("/index.html");
    }
}
