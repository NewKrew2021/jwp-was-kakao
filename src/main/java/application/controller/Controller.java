package application.controller;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
