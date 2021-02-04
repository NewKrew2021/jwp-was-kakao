package controller;

import controller.common.UrlPath;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface Controller {

    Controller defaultController = new DefaultController();
    Map<String, Controller> controllers = UrlPath.getControllers();

    static Controller of(String path) {
        Controller controller = controllers.get(path);
        return (controller == null) ? defaultController : controller;
    }

    void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
