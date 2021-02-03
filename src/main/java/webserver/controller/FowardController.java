package webserver.controller;

import utils.FileIoUtils;
import webserver.domain.ContentTypes;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.net.URISyntaxException;

public class FowardController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        if (isExistFile("./static" + httpRequest.getPath())) {
            httpResponse.addHeader(CONTENT_TYPE, ContentTypes.getTypeFromPath(httpRequest.getPath()).getType());
            httpResponse.forward("./static" + httpRequest.getPath());
            return;
        }

        if (isExistFile("./templates" + httpRequest.getPath())) {
            httpResponse.addHeader(CONTENT_TYPE, ContentTypes.getTypeFromPath(httpRequest.getPath()).getType());
            httpResponse.forward("./templates" + httpRequest.getPath());
            return;
        }

        httpResponse.response404();
    }

    private boolean isExistFile(String path) {
        try {
            return FileIoUtils.isExistFile(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

}
