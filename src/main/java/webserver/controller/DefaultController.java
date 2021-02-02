package webserver.controller;

import utils.FileIoUtils;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultController extends AbstractController {
    private static final String TEMPLATE_PREFIX = "templates";
    private static final String STATIC_PREFIX = "static";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.send(HttpStatusCode.OK, new String(readFile(httpRequest.getPath())));
        } catch (URISyntaxException | IOException e) {
            httpResponse.send(HttpStatusCode.NOT_FOUND);
        }
    }

    private byte[] readFile(String path) throws IOException, URISyntaxException {
        if (path.endsWith(".html") || path.endsWith(".ico")) {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_PREFIX + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PREFIX + path);
    }
}
