package webserver.controller;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public interface Controller {
    String CONTENT_TYPE = "Content-Type";
    String SET_COOKIE = "Set-Cookie";

    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
