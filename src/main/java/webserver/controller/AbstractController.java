package webserver.controller;

import webserver.domain.HttpMethod;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public abstract class AbstractController implements Controller {

    public void service(HttpRequest request, HttpResponse response) {
        try {
            methodMapping(request, response);
        } catch (Exception e) {
            response.send501NotImplemented();
        }
        response.send405BadMethod();
    }

    private void methodMapping(HttpRequest request, HttpResponse response) throws Exception {
        if (request.getMethod() == HttpMethod.GET) {
            doGet(request, response);
        } else if (request.getMethod() == HttpMethod.POST) {
            doPost(request, response);
        } else if (request.getMethod() == HttpMethod.PUT) {
            doPut(request, response);
        } else if (request.getMethod() == HttpMethod.DELETE) {
            doDelete(request, response);
        }
    }

    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        response.send405BadMethod();
    }

    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        response.send405BadMethod();
    }

    public void doPut(HttpRequest request, HttpResponse response) throws Exception {
        response.send405BadMethod();
    }

    public void doDelete(HttpRequest request, HttpResponse response) throws Exception {
        response.send405BadMethod();
    }
}
