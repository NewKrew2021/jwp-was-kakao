package controller;

import utils.FileIoUtils;
import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {

    private static final String STATIC_PATH = "./static";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(STATIC_PATH + httpRequest.getPath());
        httpResponse.forward(body);
    }
}
