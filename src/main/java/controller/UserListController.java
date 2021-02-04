package controller;

import exception.UserAuthorizationException;
import utils.ResourceLoader;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserListController extends AbstractController {
    private static final String NEED_LOGIN_MESSAGE = "로그인이 필요합니다.";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            validateLogin(httpRequest);
        } catch (UserAuthorizationException e) {
            logger.error(e.getMessage());
            httpResponse.sendRedirect("/user/login.html");
            return;
        }
        byte[] page = ResourceLoader.getDynamicPageWithUser("user/list");
        logger.debug("Succeeded in loading user-list page");
        httpResponse.forwardBody(page);
    }

    private void validateLogin(HttpRequest httpRequest) {
        String cookie = httpRequest.getHeader("Cookie");
        if (!isLogin(cookie)) {
            throw new UserAuthorizationException(NEED_LOGIN_MESSAGE);
        }
    }

    private boolean isLogin(String cookie) {
        return cookie != null && cookie.equals("logined=true");
    }
}