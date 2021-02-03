package controller;

import annotation.web.RequestMethod;
import http.HttpRequest;
import http.HttpResponse;

import java.util.Optional;

public class StaticController extends Controller {

    @Override
    public Optional<Handler> getResponsibleHandler(HttpRequest request) {
        String uri = request.getUri();
        if (request.getRequestMethod() == RequestMethod.GET
                && (uri.startsWith("/css") || uri.startsWith("/fonts") || uri.startsWith("/images") || uri.startsWith("/js"))) {
            return Optional.of(cssHandler);
        }
        return Optional.empty();
    }

    public static Handler cssHandler = (request) -> new HttpResponse.Builder()
            .setStatus("HTTP/1.1 200 OK")
            .setCss("./static" + request.getUri())
            .build();
}
