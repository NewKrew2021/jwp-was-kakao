package webserver.controller;

import utils.FileIoUtils;
import webserver.model.ContentType;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;
import webserver.model.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController implements Controller {
    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        ContentType contentType = ContentType.fromUrlPath(request.getPath());
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + request.getPath());
            response.sendOk(contentType, body);
            return;
        } catch (URISyntaxException | NullPointerException ignored) {
        }

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
            response.sendOk(contentType, body);
        } catch (URISyntaxException | NullPointerException e) {
            response.sendHeader(HttpStatus.NOT_FOUND);
        }
    }
}
