package controller;

import annotation.web.RequestMethod;
import http.HttpRequest;
import http.HttpResponse;

import java.util.Optional;

public class TemplateController extends Controller {

    @Override
    public Optional<Handler> getResponsibleHandler(HttpRequest request) {
        if (request.getRequestMethod() == RequestMethod.GET && request.getUri().endsWith(".html")) {
            return Optional.of(htmlHandler);
        }
        if (request.getRequestMethod() == RequestMethod.GET && request.getUri().equals("/favicon.ico")) {
            return Optional.of(faviconHandler);
        }
        return Optional.empty();
    }

    public static Handler htmlHandler = (request) -> new HttpResponse.Builder()
            .setStatus("HTTP/1.1 200 OK")
            .setHtml("./templates" + request.getUri())
            .build();

    public static Handler faviconHandler = (request) -> new HttpResponse.Builder()
            .setStatus("HTTP/1.1 200 OK")
            .setCss("./templates" + request.getUri())
            .build();
}
