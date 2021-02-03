package webserver.controller;

import webserver.model.HttpRequest;
import webserver.model.HttpResponse;

public interface Controller {
    String getPath();

    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}

