package context;

import controller.Controller;
import controller.StaticFileController;
import controller.UserCreateController;
import controller.UserLoginController;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private Map<String, Controller> controllerMap;
    private Controller defaultController;

    public ApplicationContext() {
        controllerMap = new HashMap<>();
        // TODO: controller 만들때마다 넣어줘야되네..
        controllerMap.put(UserCreateController.PATH, new UserCreateController());
        controllerMap.put(UserLoginController.PATH, new UserLoginController());

        defaultController = new StaticFileController();
    }

    public Controller getControllerForPath(String path) {
        if (controllerMap.containsKey(path)) {
            return controllerMap.get(path);
        }

        return defaultController;
    }
}
