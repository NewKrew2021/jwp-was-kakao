package webserver.controller;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class ForwardController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }
}
