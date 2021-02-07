package webserver.controller;

import annotation.web.ResponseStatus;
import webserver.http.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class DefaultController extends AbstractController {
    private static DefaultController defaultController = null;

    public static DefaultController of() {
        if (defaultController == null) {
            defaultController = new DefaultController();
        }

        return defaultController;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendStatus(ResponseStatus.NOT_FOUND);
    }

    @Override
    public boolean supports(String path) {
        return true;
    }
}
