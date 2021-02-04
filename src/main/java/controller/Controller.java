package controller;

import dto.HttpRequest;
import dto.HttpResponse;

import java.io.IOException;

public interface Controller {
    public void service(HttpRequest request, HttpResponse response) throws IOException;
}
