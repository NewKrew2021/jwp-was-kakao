package controller;

import request.HttpRequest;
import response.HttpResponse;

public class LogoutController extends AbstractController {

    private static final String INDEX_PAGE = "http://localhost:8080/index.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendNewPage(INDEX_PAGE, false);
    }

}
