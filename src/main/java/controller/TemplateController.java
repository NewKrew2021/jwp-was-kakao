package controller;

import annotation.web.RequestMethod;
import webserver.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController extends Controller {
    private static final String prefix = "./templates";

    public TemplateController() {
        super(RequestMethod.GET, "");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == RequestMethod.GET && request.getUri().endsWith(".html");
    }

    @Override
    public HttpResponse handleRequest(HttpRequest request) throws IOException, URISyntaxException {
        String path = prefix + request.getUri();
        return new HttpResponse.Builder()
                .status("HTTP/1.1 200 OK")
                .body(FileIoUtils.loadFileFromClasspath(path), FileIoUtils.getMimeType(path))
                .build();
    }
}
