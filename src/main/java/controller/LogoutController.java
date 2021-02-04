package controller;

import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;

public class LogoutController extends AbstractController {
    private static final String INDEX_HTML = "/index.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse
                .addRedirectionLocationHeader(INDEX_HTML)
                .addSetCookieHeader(false)
                .send(HttpResponseStatusCode.FOUND)
                .build();
    }

}
