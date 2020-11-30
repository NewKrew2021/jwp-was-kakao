package context;

import controller.Controller;
import controller.StaticFileController;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private Map<String, Controller> controllerMap;
    private Controller defaultController;

    public ApplicationContext() {
        controllerMap = new HashMap<>();

        defaultController = new StaticFileController();
    }

    public Controller getControllerForPath(String path) {
        if (controllerMap.containsKey(path)) {
            return controllerMap.get(path);
        }

        return defaultController;
    }
}
