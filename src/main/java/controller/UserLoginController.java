package controller;

import utils.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserLoginController extends AbstractController {
    @Override
    public String getPath() {
        return "/user/login";
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        if (UserService.isInValidUser(userId, password)) {
            logger.debug("login failed");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
        logger.debug("login success");
        httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
        httpResponse.sendRedirect("/");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
