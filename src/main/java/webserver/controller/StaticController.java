package webserver.controller;

import webserver.http.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class StaticController extends AbstractController {
    private static final String STATICS_DIRECTORY = "./static";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(STATICS_DIRECTORY + httpRequest.getPath());
    }
}
