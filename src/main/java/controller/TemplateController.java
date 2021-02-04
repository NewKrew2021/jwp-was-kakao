package controller;

import annotation.web.RequestMethod;
import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

import java.util.Optional;

public class TemplateController extends Controller {
    private static final String prefix = "./templates";

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

    public static Handler htmlHandler = (request) -> {
        String path = prefix + request.getUri();
        return new HttpResponse.Builder()
                .status("HTTP/1.1 200 OK")
                .body(FileIoUtils.loadFileFromClasspath(path), FileIoUtils.getMimeType(path))
                .build();
    };

    public static Handler faviconHandler = (request) -> {
        String path = prefix + request.getUri();
        return new HttpResponse.Builder()
                .status("HTTP/1.1 200 OK")
                .body(FileIoUtils.loadFileFromClasspath(path), FileIoUtils.getMimeType(path))
                .build();
    };
}
