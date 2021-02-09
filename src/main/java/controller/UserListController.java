package controller;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.HttpException;
import utils.HandlebarsUtils;

public class UserListController extends AbstractController {
    private static final String USER_LOGIN_HTML_URL = "/user/login.html";
    private static final String USER_LIST_URL = "/user/list";

    private static final String LOGIN_COOKIE_KEY = "logined";
    private static final String LOGIN_COOKIE_VALUE_FALSE = "false";

    @Override
    HttpResponse doGet(HttpRequest httpRequest) throws HttpException {
        if (httpRequest.getCookie(LOGIN_COOKIE_KEY) == null
                || httpRequest.getCookie(LOGIN_COOKIE_KEY).equals(LOGIN_COOKIE_VALUE_FALSE)) {
            return new HttpResponse.Builder()
                    .redirect(USER_LOGIN_HTML_URL)
                    .build();
        }

        return new HttpResponse.Builder()
                .ok(makeProfilePage().getBytes())
                .build();
    }

    private String makeProfilePage() throws HttpException {
        return HandlebarsUtils.render("user/list", DataBase.findAll());
    }

    @Override
    public boolean isSupport(String path) {
        return path.equals(USER_LIST_URL);
    }

}
