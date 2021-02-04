package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserLoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    private static final String PATH = "/user/login";


    private static UserLoginController instance;

    private UserLoginController() {
    }

    public static UserLoginController getInstance() {
        if (instance == null) {
            instance = new UserLoginController();
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
        // There is no matching action, so it does nothing.
    }
}
