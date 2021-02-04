package webserver.controller;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

public class DefaultController extends AbstractController {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        if (httpRequest.isTemplate()) {
            return new HttpResponse.Builder()
                    .status(HttpStatusCode.OK)
                    .contentType("text/html;charset=utf-8")
                    .body("templates" + httpRequest.getPath())
                    .build();
        }
        return new HttpResponse.Builder()
                .status(HttpStatusCode.OK)
                .contentType("text/css;charset=utf-8")
                .body("static" + httpRequest.getPath())
                .build();
    }
}
