package controller;

import dto.HttpRequest;
import dto.HttpResponse;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.getMethod().equals("GET")) {
            doGet(request, response);
        }

        if (request.getMethod().equals("POST")) {
            doPost(request, response);
        }
    }

    public void doPost(HttpRequest request, HttpResponse response){}

    public void doGet(HttpRequest request, HttpResponse response){}
}
