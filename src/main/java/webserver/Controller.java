package webserver;

import annotation.web.RequestMethod;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class Controller {
    protected RequestMethod requestMethod;
    protected String uri;

    protected Controller(RequestMethod requestMethod, String uri) {
        this.requestMethod = requestMethod;
        this.uri = uri;
    }

    public abstract boolean canHandle(HttpRequest request);

    public abstract String handleRequest(HttpRequest request, HttpResponse response, Model model);
}
