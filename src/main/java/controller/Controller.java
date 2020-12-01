package controller;

import exceptions.NoSuchResource;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class Controller {

    public HttpResponse handleRequest(HttpRequest httpRequest) {
        try {
            return handleMethod(httpRequest);
        } catch (NoSuchResource e) {
            return HttpResponse._404_NOT_FOUND;
        }
    }

    private HttpResponse handleMethod(HttpRequest httpRequest) {
        if (HttpMethod.GET.equals(httpRequest.getMethod())) {
            return handleGet(httpRequest);
        }
        if (HttpMethod.POST.equals(httpRequest.getMethod())) {
            return handlePost(httpRequest);
        }
        if (HttpMethod.PUT.equals(httpRequest.getMethod())) {
            return handlePut(httpRequest);
        }
        return HttpResponse._405_METHOD_NOT_ALLOWED;
    }

    protected HttpResponse handleGet(HttpRequest httpRequest) {
        return HttpResponse._405_METHOD_NOT_ALLOWED;
    }

    protected HttpResponse handlePost(HttpRequest httpRequest) {
        return HttpResponse._405_METHOD_NOT_ALLOWED;
    }

    protected HttpResponse handlePut(HttpRequest httpRequest) {
        return HttpResponse._405_METHOD_NOT_ALLOWED;
    }
}
