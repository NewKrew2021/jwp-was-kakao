package webserver.controller;

import utils.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.net.URISyntaxException;

public class FowardController extends AbstractController {
    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doGet(httpRequest, httpResponse);

        if (isExistFile("./static" + httpRequest.getPath())) {
            httpResponse.addHeader("Accept", httpRequest.getHeader("Accept"));
            httpResponse.forward("./static" + httpRequest.getPath());
            return;
        }

        if (isExistFile("./templates" + httpRequest.getPath())) {
            httpResponse.addHeader("Accept", "text/html;charset=utf-8");
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
