package service.controller;

import framework.controller.Controller;

import java.util.HashMap;
import java.util.Map;

public enum UrlPath {

    USER_LIST("/user/list", new ListUserController()),
    USER_CREATE("/user/create", new CreateUserController()),
    USER_LOGIN("/user/login", new LoginController());

    private final String path;
    private final Controller controller;

    UrlPath(String path, Controller controller) {
        this.path = path;
        this.controller = controller;
    }

    public static Map<String, Controller> getControllers() {
        Map<String, Controller> controllers = new HashMap<>();
        for(UrlPath urlPath : UrlPath.values()) {
            controllers.put(urlPath.path, urlPath.controller);
        }
        return controllers;
    }

}
