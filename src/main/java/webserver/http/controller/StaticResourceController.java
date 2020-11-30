package webserver.http.controller;

import utils.ClasspathFileLoader;
import utils.FileLoader;
import webserver.http.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class StaticResourceController implements Controller {

    private FileLoader fileLoader;

    public StaticResourceController(String basePath) {
        fileLoader = new ClasspathFileLoader(basePath);
    }

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] body = fileLoader.load(httpRequest.getPath());
        httpResponse.setBody(body);
    }
}
