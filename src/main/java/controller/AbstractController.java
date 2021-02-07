package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.getMethod().equals("GET")) {
            doGet(request, response);
        }

        if (request.getMethod().equals("POST")) {
            doPost(request, response);
        }
    }

    public abstract void doPost(HttpRequest request, HttpResponse response);

    public abstract void doGet(HttpRequest request, HttpResponse response);
}
