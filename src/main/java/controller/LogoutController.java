package controller;

import request.HttpRequest;
import response.HttpResponse;

import java.util.Optional;

public class LogoutController extends AbstractController {

    private static final String INDEX_PAGE = "/index.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpRequest.setLogin(false);
        httpResponse.sendNewPage(INDEX_PAGE, httpRequest.getSessionId());
    }

}
