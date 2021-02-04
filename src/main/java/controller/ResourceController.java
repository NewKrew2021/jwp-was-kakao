package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ResourceLoader;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class ResourceController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private static final String PATH = "/";

    private static ResourceController instance;

    private ResourceController() {
    }

    public static ResourceController getInstance() {
        if (instance == null) {
            instance = new ResourceController();
        }
        return instance;
    }

    @Override
    public boolean match(String path) {
        return PATH.equals(path);
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        // There is no matching action, so it does nothing.
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        if (!isResource(path)) {
            logger.debug(path + " is not resource");
            httpResponse.badRequest();
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
