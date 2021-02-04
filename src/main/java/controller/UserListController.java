package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ResourceLoader;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private static final String PATH = "/user/list";

    private static UserListController instance;

    private UserListController() {
    }

    public static UserListController getInstance() {
        if (instance == null) {
            instance = new UserListController();
        }
        return instance;
    }

    @Override
    public boolean match(String path) {
        return PATH.equals(path);
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        // There is no matching action, so it does nothing.
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            validate(httpRequest);
            byte[] page = ResourceLoader.getDynamicPage("user/list");
            logger.debug("Succeeded in loading user-list page");
            httpResponse.forwardBody(page);
        } catch (RuntimeException e) {
            logger.debug(e.getMessage());
            httpResponse.sendRedirect("/user/login.html");
        }
    }

    private void validate(HttpRequest httpRequest) {
        String cookie = httpRequest.getHeader("Cookie");
        if (!isLogined(cookie)) {
            String message = "비로그인 사용자입니다.";
            logger.error(message);
            throw new RuntimeException(message);
        }
    }

    private boolean isLogined(String cookie) {
        return cookie != null && cookie.equals("logined=true");
    }
}
