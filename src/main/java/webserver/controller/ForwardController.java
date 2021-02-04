package webserver.controller;

import webserver.http.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ForwardController extends AbstractController {
    private static final String TEMPLATES_DIRECTORY = "./templates";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(TEMPLATES_DIRECTORY + httpRequest.getPath());
    }

    @Override
    public boolean supports(String path) {
        return path.endsWith(".html") || path.endsWith("favicon.ico");
    }
}
