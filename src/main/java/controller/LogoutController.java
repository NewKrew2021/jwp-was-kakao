package controller;

import request.HttpRequest;
import response.HttpResponse;
import service.UserService;

public class LogoutController extends AbstractController {

    private static final String INDEX_PAGE = "/index.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.loginOutUser(httpRequest.getSessionId());
        httpResponse.sendNewPage(INDEX_PAGE, httpRequest.getSessionId());
    }

}
