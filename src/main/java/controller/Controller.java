package controller;

import dto.HttpRequest;
import dto.HttpResponse;

public interface Controller {
    public void service(HttpRequest request, HttpResponse response);
}
