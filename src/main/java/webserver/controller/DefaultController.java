package webserver.controller;

import annotation.web.ResponseStatus;
import webserver.http.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class DefaultController extends AbstractController {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendStatus(ResponseStatus.NOT_FOUND);
    }
}
