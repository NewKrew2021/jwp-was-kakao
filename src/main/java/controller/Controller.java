package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import exception.HttpException;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest) throws HttpException;
    boolean isSupport(String path);
}

