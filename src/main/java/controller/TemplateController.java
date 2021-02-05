package controller;

import annotation.web.RequestMethod;
import webserver.Controller;
import webserver.Model;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class TemplateController extends Controller {
    public TemplateController() {
        super(RequestMethod.GET, "");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == RequestMethod.GET && request.getUri().endsWith(".html");
    }

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response, Model model) {
        return "template";
    }
}
