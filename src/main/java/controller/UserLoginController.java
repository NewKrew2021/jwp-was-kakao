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
    private static final String INDEX_URL = "/index.html";
    private static final String LOGIN_FAIL_URL = "/user/login_failed.html";
    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "password";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String userId = httpRequest.getParameter(USER_ID);
        String password = httpRequest.getParameter(USER_PASSWORD);

        Optional<User> user = DataBase.findUserById(userId);
        try {
            user.filter(u -> u.hasPassword(password)).orElseThrow(RuntimeException::new);
            httpResponse.setCookie(TRUE);
            httpResponse.sendRedirect(INDEX_URL);
        } catch (RuntimeException e) {
            httpResponse.setCookie(FALSE);
            httpResponse.sendRedirect(LOGIN_FAIL_URL);
        }
    }
}