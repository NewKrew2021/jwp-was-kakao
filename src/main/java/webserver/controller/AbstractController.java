package webserver.controller;

import webserver.domain.HttpMethod;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

public abstract class AbstractController implements Controller {

    public HttpResponse service(HttpRequest request) {
        try {
            return methodMapping(request);
        } catch (Exception e) {
            return new HttpResponse.Builder().status(HttpStatusCode.INTERNAL_SERVER_ERROR).build();
        }
    }

    private HttpResponse methodMapping(HttpRequest request) throws Exception {
        if (request.getMethod() == HttpMethod.GET) {
            return doGet(request);
        } else if (request.getMethod() == HttpMethod.POST) {
            return doPost(request);
        } else if (request.getMethod() == HttpMethod.PUT) {
            return doPut(request);
        } else if (request.getMethod() == HttpMethod.DELETE) {
            return doDelete(request);
        }
        return new HttpResponse.Builder().status(HttpStatusCode.METHOD_NOT_ALLOWED).build();
    }

    public HttpResponse doPost(HttpRequest request) throws Exception {
        return new HttpResponse.Builder().status(HttpStatusCode.METHOD_NOT_ALLOWED).build();
    }

    public HttpResponse doGet(HttpRequest request) throws Exception {
        return new HttpResponse.Builder().status(HttpStatusCode.METHOD_NOT_ALLOWED).build();
    }

    public HttpResponse doPut(HttpRequest request) throws Exception {
        return new HttpResponse.Builder().status(HttpStatusCode.METHOD_NOT_ALLOWED).build();
    }

    public HttpResponse doDelete(HttpRequest request) throws Exception {
        return new HttpResponse.Builder().status(HttpStatusCode.METHOD_NOT_ALLOWED).build();
    }
}
