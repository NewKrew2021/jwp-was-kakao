package controller;

import exception.NotDefinedMethodException;
import http.HttpRequest;
import http.HttpResponse;

public class DefaultController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        throw new NotDefinedMethodException();
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        response.forward(getContentLocation(request.getUri()));
    }
}
