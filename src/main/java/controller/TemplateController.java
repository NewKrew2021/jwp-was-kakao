package controller;

import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController extends AbstractController {

    private static final String TEMPLATES_PATH = "./templates";

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + httpRequest.getPath());
        httpResponse.forward(body);
    }
}
