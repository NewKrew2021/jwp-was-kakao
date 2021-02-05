package webserver.controller;

import utils.FileIoUtils;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.send(HttpStatusCode.OK, FileIoUtils.readStaticHttpFile(httpRequest.getPath()));
        } catch (URISyntaxException | IOException e) {
            httpResponse.send(HttpStatusCode.NOT_FOUND);
        }
    }

}
