package webserver.service;

import com.google.common.collect.Maps;
import user.controller.CreateUserController;
import user.controller.ListUserController;
import user.controller.LoginController;
import user.controller.UserProfileController;
import webserver.controller.DefaultController;
import webserver.controller.ForwardController;
import webserver.controller.StaticController;
import webserver.http.Controller;
import webserver.http.RequestMapping;

import java.util.Map;

public class ManualRequestMapping implements RequestMapping {
    private final Map<String, Controller> controllers = Maps.newHashMap();
    private final Controller forwardController = new ForwardController();
    private final Controller staticController = new StaticController();
    private final Controller defaultController = new DefaultController();

    public ManualRequestMapping() {
        controllers.put("/user/profile", new UserProfileController());
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/login", new LoginController());
    }

    @Override
    public Controller getController(String path) {
        if (isTemplateOrFavicon(path)) {
            return forwardController;
        }

        if (isFile(path)) {
            return staticController;
        }

        return controllers.getOrDefault(path.toLowerCase(), defaultController);
    }

    private boolean isTemplateOrFavicon(String path) {
        return path.endsWith(".html") || path.endsWith("favicon.ico");
    }

    private boolean isFile(String path) {
        return path.matches("\\.\\w+$");
    }
}
