package controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerEntity {
    private static Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/login.html", new UserLoginController());
        controllers.put("/user/login", new UserLoginController());
        controllers.put("/user/login_failed.html", new UserLoginController());
        controllers.put("/user/list.html", new UserListController());
        controllers.put("/user/create", new UserCreateController());
    }

    public static Controller getControllers(String path) {
        return controllers.getOrDefault(path, new ForwardController());
    }
}
