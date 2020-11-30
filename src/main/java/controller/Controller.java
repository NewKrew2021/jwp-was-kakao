package controller;

import exceptions.NoSuchResource;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {

    HttpResponse handleRequest(HttpRequest httpRequest) throws NoSuchResource;
}
