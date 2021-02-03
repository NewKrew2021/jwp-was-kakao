package webserver.controller;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {

    void service(HttpRequest httpRequest, HttpResponse httpResponse);

}
