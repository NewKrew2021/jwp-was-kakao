package controller;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.HttpException;
import org.springframework.http.HttpStatus;

public class UserLoginController extends AbstractController {
    private static final String INDEX_URL = "/index.html";
    private static final String LOGIN_URL = "/user/login";
    private static final String LOGIN_FAIL_URL = "/user/login_failed.html";

    private static final String PARAMETER_NAME_ID = "userId";
    private static final String PARAMETER_NAME_PASSWORD = "password";

    private static final String LOGIN_COOKIE_KEY = "logined";
    private static final String LOGIN_COOKIE_VALUE_TRUE = "true";
    private static final String LOGIN_COOKIE_VALUE_FALSE = "false";
    private static final String LOGIN_COOKIE_PATH = "/";

    @Override
    HttpResponse doPost(HttpRequest httpRequest) throws HttpException {
        if (DataBase.login(httpRequest.getParameter(PARAMETER_NAME_ID), httpRequest.getParameter(PARAMETER_NAME_PASSWORD))) {
            return new HttpResponse.Builder()
                    .redirect(INDEX_URL)
                    .setCookie(LOGIN_COOKIE_KEY, LOGIN_COOKIE_VALUE_TRUE, LOGIN_COOKIE_PATH)
                    .build();
        }

        return new HttpResponse.Builder()
                .redirect(LOGIN_FAIL_URL)
                .setCookie(LOGIN_COOKIE_KEY, LOGIN_COOKIE_VALUE_FALSE, LOGIN_COOKIE_PATH)
                .build();
    }

    @Override
    public boolean isSupport(String path) {
        return path.equals(LOGIN_URL);
    }
}
