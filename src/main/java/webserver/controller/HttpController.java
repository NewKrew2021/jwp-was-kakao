package webserver.controller;

import utils.FileIoUtils;
import webserver.RequestMappingHandler;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpController{

    public HttpResponse response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return new HttpResponse().error();
    }

    public byte[] setView(String viewName) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(RequestMappingHandler.TEMPLATES_RESOURCE_PATH_PREFIX + viewName);
    }

    public byte[] setFile(String viewName) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(RequestMappingHandler.STATIC_RESOURCE_PATH_PREFIX + viewName);
    }
}

