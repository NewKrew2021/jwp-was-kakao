package controller;

import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import service.LogoutService;

public class LogoutController extends AbstractController {
    private static final String INDEX_HTML = "/index.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        LogoutService logoutService = new LogoutService();
        logoutService.logout(httpRequest);

        httpResponse
                .addRedirectionLocationHeader(INDEX_HTML)
                .send(HttpResponseStatusCode.FOUND)
                .build();
    }

}
