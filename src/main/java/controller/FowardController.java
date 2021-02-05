package controller;

import utils.FileIoUtils;
import http.HttpRequest;
import http.HttpResponse;

import java.net.URISyntaxException;

public class FowardController extends AbstractController {
    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isExistFile("./static" + httpRequest.getPath().getPagePath())) {
            httpResponse.forwardStatic("./static" + httpRequest.getPath().getPagePath());
            return;
        }

        if (isExistFile("./templates" + httpRequest.getPath().getPagePath())) {
            httpResponse.forwardTemplate("./templates" + httpRequest.getPath().getPagePath());
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
