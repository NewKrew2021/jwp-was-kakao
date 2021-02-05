package application.controller;

import utils.FileIoUtils;
import webserver.domain.ContentTypes;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class FowardController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getPath());
            httpResponse.response200Ok(body,ContentTypes.getTypeFromPath(httpRequest.getPath()).getType());
            return;
        } catch (IOException | URISyntaxException | NullPointerException e) {}

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getPath());
            httpResponse.response200Ok(body,ContentTypes.getTypeFromPath(httpRequest.getPath()).getType());
            return;
        } catch (IOException | URISyntaxException | NullPointerException e) {}

        httpResponse.response404();
    }

}
