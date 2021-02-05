package controller;

import dto.HttpRequest;
import dto.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
