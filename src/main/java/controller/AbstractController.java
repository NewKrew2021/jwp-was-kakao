package controller;

import request.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response)  throws IOException, URISyntaxException {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
        }
        if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.badRequest();
    };

    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.badRequest();
    };

}
