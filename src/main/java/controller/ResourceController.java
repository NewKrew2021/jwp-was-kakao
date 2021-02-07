package controller;

import exception.NotFoundException;
import model.Resource;
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
    public HttpResponse doPost(HttpRequest request) {
        // There is no matching action, so it does nothing.
        throw new NotFoundException("요청에 매칭되는 동작이 없습니다.");
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        String path = request.getPath();
        Resource resource = ResourceLoader.getResource(path);
        logger.debug("{} 로딩 성공", path);
        return HttpResponse.ok(resource);
    }
}
