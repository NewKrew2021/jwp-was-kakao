package controller;

import request.HttpRequest;
import response.HttpResponse;

public class LogoutController extends AbstractController {
    private static final String INDEX_HTML = "/index.html";
    private static final String FALSE = "false";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.response302Header(INDEX_HTML, FALSE);
    }

}
