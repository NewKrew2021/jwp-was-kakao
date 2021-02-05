package webserver.controller;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public interface Controller {
    HttpResponse service(HttpRequest request);
}
