package webserver.controller;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class HttpAbstractController implements HttpController {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }


    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
    }

    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
    }


}

