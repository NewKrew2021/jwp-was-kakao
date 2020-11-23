package webserver.http.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {
    void execute(HttpRequest httpRequest, HttpResponse httpResponse);
}
