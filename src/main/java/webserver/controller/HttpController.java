package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface HttpController{

    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}

