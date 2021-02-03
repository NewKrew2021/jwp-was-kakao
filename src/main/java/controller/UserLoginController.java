package controller;

import service.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserLoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (UserService.isValidUser(httpRequest)) {
            logger.debug("login failed");
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        logger.debug("login success");
        httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
