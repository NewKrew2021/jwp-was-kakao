package controller;

import annotation.web.RequestMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class Controller {
    protected RequestMethod requestMethod;
    protected String uri;

    protected Controller(RequestMethod requestMethod, String uri) {
        this.requestMethod = requestMethod;
        this.uri = uri;
    }

    public abstract boolean canHandle(HttpRequest request);

    public abstract HttpResponse handleRequest(HttpRequest request) throws IOException, URISyntaxException;
}
