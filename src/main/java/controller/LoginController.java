package controller;

import request.HttpRequest;
import response.HttpResponse;
import service.UserService;

public class LoginController extends AbstractController {

    private static final String INDEX_PAGE = "http://localhost:8080/index.html";
    private static final String LOGIN_FAIL_PAGE = "http://localhost:8080/user/login_failed.html";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";


    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (UserService.isLoginSuccess(httpRequest.getParameter(USER_ID), httpRequest.getParameter(PASSWORD))) {
            httpResponse.sendNewPage(INDEX_PAGE, true);
            return;
        }
        httpResponse.sendNewPage(LOGIN_FAIL_PAGE, false);
    }

}
