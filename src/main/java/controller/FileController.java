package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class FileController extends AbstractController {

    private FileController() {
    }

    public static FileController getInstance() {
        return FileController.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FileController INSTANCE = new FileController();
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.forward(request.getPath());
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
