package webserver.controller;

import webserver.model.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerMapper {

    private static Map<String, Controller> map = new HashMap<>();
    private static Controller defaultController;
    private static final ControllerMapper controllerMapper = new ControllerMapper();

    static {
        List<Controller> controllers = initControllers();
        mapControllers(controllers);
        initDefaultController();
    }

    public static ControllerMapper getInstance() {
        return controllerMapper;
    }

    public Controller assignController(HttpRequest httpRequest) {
        return map.getOrDefault(httpRequest.getPath(), defaultController);
    }

    private static List<Controller> initControllers() {
        List<Controller> controllers = new ArrayList<>();

        controllers.add(new UserCreateController());
        controllers.add(new UserLoginController());
        controllers.add(new UserLogoutController());
        controllers.add(new UserListController());

        return controllers;
    }

    private static void initDefaultController() {
        defaultController = new FileController();
    }

    private static void mapControllers(List<Controller> controllers) {
        for (Controller controller : controllers) {
            map.put(controller.getPath(), controller);
        }
    }
}
