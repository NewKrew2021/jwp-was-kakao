package controller;

import annotation.web.RequestMethod;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class FaviconController extends Controller {
    private static final String prefix = "./templates";

    public FaviconController() {
        super(RequestMethod.GET, "/favicon.ico");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == requestMethod && request.getUri().equals(uri);
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
