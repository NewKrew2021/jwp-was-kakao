package controller;

import annotation.web.RequestMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import utils.FileIoUtils;

import java.util.Optional;

public class StaticController extends Controller {
    private static final String prefix = "./static";

    @Override
    public Optional<Handler> getResponsibleHandler(HttpRequest request) {
        String uri = request.getUri();
        if (request.getRequestMethod() == RequestMethod.GET
                && (uri.startsWith("/css") || uri.startsWith("/fonts") || uri.startsWith("/images") || uri.startsWith("/js"))) {
            return Optional.of(cssHandler);
        }
        return Optional.empty();
    }

    private Handler cssHandler = (request) -> {
        String path = prefix + request.getUri();
        return new HttpResponse.Builder()
                .status("HTTP/1.1 200 OK")
                .body(FileIoUtils.loadFileFromClasspath(path), FileIoUtils.getMimeType(path))
                .build();
    };
}
