package webserver.controller;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

public abstract class AbstractController implements Controller {

    public void service(HttpRequest request, HttpResponse response) {
        try {
            methodMapping(request, response);
        } catch (Exception e) {
            response.send(HttpStatusCode.NOT_IMPLEMENTED);
        }
    }

    private void methodMapping(HttpRequest request, HttpResponse response) {
        switch (request.getMethod()) {
            case GET:
                doGet(request, response);
                break;
            case POST:
                doPost(request, response);
                break;
            case PUT:
                doPut(request, response);
                break;
            case DELETE:
                doDelete(request, response);
                break;
            default:
                response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
        }
    }

    public void doPost(HttpRequest request, HttpResponse response) {
        response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    public void doGet(HttpRequest request, HttpResponse response) {
        response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    public void doPut(HttpRequest request, HttpResponse response) {
        response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    public void doDelete(HttpRequest request, HttpResponse response) {
        response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
    }
}
