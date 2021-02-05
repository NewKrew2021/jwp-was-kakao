package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class FileController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.forward(request.getPath());
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
