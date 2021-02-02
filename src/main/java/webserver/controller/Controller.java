package webserver.controller;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
