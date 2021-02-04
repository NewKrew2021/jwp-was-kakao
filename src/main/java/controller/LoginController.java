package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import service.LoginService;

import java.util.Optional;

public class LoginController extends AbstractController {

    private static final String LOGIN_FAIL_PAGE = "/user/login_failed.html";
    private static final String INDEX_HTML = "/index.html";
    private final LoginService loginService = new LoginService();
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (loginService.login(httpRequest)) {
            httpResponse
                    .addRedirectionLocationHeader(INDEX_HTML)
                    .addSetCookieHeader(true)
                    .send(HttpResponseStatusCode.FOUND)
                    .build();
            return;
        }
        httpResponse
                .addRedirectionLocationHeader(LOGIN_FAIL_PAGE)
                .send(HttpResponseStatusCode.FOUND)
                .build();
    }

}
