package controller;

import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public interface Controller {

    Controller defaultController = new DefaultController();
    Map<String, Controller> controllers = putMapping();

    static Map<String, Controller> putMapping() {
        Map<String, Controller> controllers = new HashMap<>();
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        return controllers;
    }

    static Controller of(String path) {
        Controller controller = controllers.get(path);
        return (controller == null) ? defaultController : controller;
    }

    void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
