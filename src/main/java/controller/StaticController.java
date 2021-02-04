package controller;

import annotation.web.RequestMethod;
import webserver.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends Controller {
    private static final String prefix = "./static";

    public StaticController() {
        super(RequestMethod.GET, "");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == RequestMethod.GET
                && (uri.startsWith("/css") || uri.startsWith("/fonts") || uri.startsWith("/images") || uri.startsWith("/js"));
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
