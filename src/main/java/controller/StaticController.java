package controller;

import utils.FileIoUtils;
import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getPath());
        httpResponse.forward(body);
    }
}
