package controller;

import service.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;
import auth.HttpSession;
import auth.SessionsManager;

public class UserLoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (UserService.isValidUser(httpRequest)) {
            logger.debug("login failed");
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        logger.debug("login success");
        HttpSession httpSession = SessionsManager.createNewSession();
        httpResponse.addHeader("Set-Cookie", "sessionId=" + httpSession.getId() + "; Path=/");
        httpResponse.sendRedirect("/index.html");
    }
}
