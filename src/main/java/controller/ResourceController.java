package controller;

import utils.ResourceLoader;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class ResourceController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        if (!isResource(path)) {
            logger.debug(path + " is not resource");
            httpResponse.notFound();
            return;
        }

        byte[] body = ResourceLoader.getBytes(path);
        logger.debug("Succeeded in loading " + path);
        String contentType = httpRequest.getHeader("Accept")
                .split(";")[0]
                .split(",")[0];
        httpResponse.addHeader("Content-Type", contentType);
        httpResponse.forwardBody(body);
    }

    private boolean isResource(String path) {
        int idx = path.lastIndexOf('.');
        return idx != -1 && idx < path.length() - 1;
    }
}
