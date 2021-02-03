package controller;

import dto.HttpRequest;
import dto.HttpResponse;

public class FileController extends AbstractController {

    public void doGet(HttpRequest request, HttpResponse response) {
        response.forward(request.getPath());
    }
}
