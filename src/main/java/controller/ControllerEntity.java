package controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerEntity {
    private static Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/index.html", new IndexController());
        controllers.put("/user/login.html", new UserLoginPageController());
        controllers.put("/user/login", new UserLoginController());
        controllers.put("/user/login_failed.html", new UserLoginFailedController());
        controllers.put("/user/list.html", new UserListController());
        controllers.put("/user/create", new UserCreateController());
        controllers.put("/user/form.html", new UserFormController());
        controllers.put("/favicon.ico", new FaviconController());

        controllers.put("/css", new CssController());
        controllers.put("/js", new JsController());
        controllers.put("/fonts", new JsController());
    }

    public static Controller getControllers(String path) {
        return controllers.get(path);
    }
}
