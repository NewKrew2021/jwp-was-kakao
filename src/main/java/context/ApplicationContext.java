package context;

import controller.*;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private static final Map<String, Controller> controllerMap;
    private static final Controller defaultController;

    static {
        controllerMap = new HashMap<>();
        // TODO: controller 만들때마다 넣어줘야되네..
        controllerMap.put(UserCreateController.PATH, new UserCreateController());
        controllerMap.put(UserLoginController.PATH, new UserLoginController());
        controllerMap.put(UserListController.PATH, new UserListController());

        defaultController = new StaticFileController();
    }

    public static Controller getControllerForPath(String path) {
        if (controllerMap.containsKey(path)) {
            return controllerMap.get(path);
        }

        return defaultController;
    }
}
