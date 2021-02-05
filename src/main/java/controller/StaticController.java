package controller;

import annotation.web.RequestMethod;
import webserver.Controller;
import webserver.Model;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class StaticController extends Controller {

    public StaticController() {
        super(RequestMethod.GET, "");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        String uri = request.getUri();
        return request.getRequestMethod() == requestMethod
                && (uri.startsWith("/css") || uri.startsWith("/fonts") || uri.startsWith("/images") || uri.startsWith("/js"));
    }

    @Override
    public String handleRequest(HttpRequest request, HttpResponse httpResponse, Model model) {
        return "static";
    }

}
