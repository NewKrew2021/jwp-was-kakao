package controller;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class UserLoginController extends AbstractController {
    private static final String INDEX_URL = "http://localhost:8080/index.html";
    private static final String LOGIN_FAIL_URL = "http://localhost:8080/user/login_failed.html";
    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "password";
    private static final String TRUE = "logined=true";
    private static final String FALSE = "logined=false";
    private static final String SCOPE = "; Path=/";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter(USER_ID);
        String password = httpRequest.getParameter(USER_PASSWORD);

        Optional<User> user = DataBase.findUserById(userId);
        try {
            user.filter(u -> u.hasSamePassword(password)).orElseThrow(RuntimeException::new);
            httpResponse.setCookie(TRUE, SCOPE);
            httpResponse.sendRedirect(INDEX_URL);
        } catch (RuntimeException e) {
            httpResponse.setCookie(FALSE, SCOPE);
            httpResponse.sendRedirect(LOGIN_FAIL_URL);
        }
    }
}
