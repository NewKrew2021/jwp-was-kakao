package webserver.service;

import webserver.http.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class StaticController extends AbstractController {
    private static final String STATICS_DIRECTORY = "./static";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(STATICS_DIRECTORY + httpRequest.getPath());
    }

    @Override
    public boolean supports(String path) {
        boolean isFile = path.matches(".*\\.\\w+$");
        return isFile;
    }
}
