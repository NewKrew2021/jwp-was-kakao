package controller;

import request.HttpRequest;
import response.HttpResponse;
import service.UserService;

import java.util.Optional;


public class ListUserController extends AbstractController {

    private static final String LOGIN_PAGE = "http://localhost:8080/user/login.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isLogined()) {
            httpResponse.forwardBody(httpResponse.responseTemplateBody(UserService.findAllUser()));
            return;
        }
        httpResponse.sendNewPage(LOGIN_PAGE, httpRequest.getSessionId());
    }

}
