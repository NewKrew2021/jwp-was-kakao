package context;

import controller.*;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationContext {

    private static final Map<String, Controller> controllerMap;
    private static final Controller defaultController;

    static {
        controllerMap = Stream.of(
                new UserCreateController(),
                new UserLoginController(),
                new UserListController())
                .collect(Collectors.toMap(Controller::getPath, x -> x));

        defaultController = new StaticFileController();
    }

    public static Controller getControllerForPath(String path) {
        if (controllerMap.containsKey(path)) {
            return controllerMap.get(path);
        }

        return defaultController;
    }
}
