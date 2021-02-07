package controller;

import request.HttpRequest;
import response.HttpResponse;
import service.UserService;


public class ListUserController extends AbstractController {

    private static final String LOGIN_PAGE = "/user/login.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (UserService.isLogined(httpRequest.getSessionId())) {
            httpResponse.forwardBody(httpResponse.responseTemplateBody(UserService.findAllUser()));
            return;
        }
        httpResponse.sendNewPage(LOGIN_PAGE, httpRequest.getSessionId());
    }

}
