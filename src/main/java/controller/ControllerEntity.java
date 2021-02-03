package controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerEntity {
    public static final String JS = "js";
    public static final String CSS = "css";
    public static final String FONTS = "fonts";
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

        controllers.put("/static", new StaticController());
    }

    public static Controller getControllers(String path) {
        return controllers.get(filterPath(path));
    }

    private static String filterPath(String path) {
        String prefix = path.split("/")[1];

        if (prefix.equals(JS) || prefix.equals(CSS) || prefix.equals(FONTS)) {
            return "/static";
        }
        return path;
    }
}
