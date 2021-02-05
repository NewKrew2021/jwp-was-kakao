package controller;

import annotation.web.RequestMethod;

import webserver.Controller;
import webserver.Model;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class FaviconController extends Controller {

    public FaviconController() {
        super(RequestMethod.GET, "/favicon.ico");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == requestMethod && request.getUri().equals(uri);
    }

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response, Model model) {
        return "favicon";
    }
}
