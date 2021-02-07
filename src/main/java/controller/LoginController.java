package controller;

import dto.UserDto;
import request.HttpRequest;
import response.HttpResponse;
import service.UserService;

public class LoginController extends AbstractController {

    private static final String INDEX_PAGE = "/index.html";
    private static final String LOGIN_FAIL_PAGE = "/user/login_failed.html";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (UserService.isLoginSuccess(UserDto.from(httpRequest))) {
            UserService.loginUser(httpRequest.getSessionId());
            httpResponse.sendNewPage(INDEX_PAGE, httpRequest.getSessionId());
            return;
        }
        httpResponse.sendNewPage(LOGIN_FAIL_PAGE, httpRequest.getSessionId());
    }

}
