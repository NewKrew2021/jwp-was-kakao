package webserver;

import webserver.controller.*;
import webserver.model.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerMapper {

    private List<Controller> controllers = new ArrayList<>();
    private Controller defaultController;
    private Map<String, Controller> map = new HashMap<>();

    public ControllerMapper() {
        initControllers();
        initDefaultController();
        initMap();
    }

    public Controller assignController(HttpRequest httpRequest) {
        return map.getOrDefault(httpRequest.getPath(), defaultController);
    }

    private void initControllers() {
        controllers.add(new UserCreateController());
        controllers.add(new UserLoginController());
        controllers.add(new UserListController());
    }

    private void initDefaultController() {
        defaultController = new FileController();
    }

    private void initMap() {
        for (Controller controller : controllers) {
            map.put(controller.getPath(), controller);
        }
    }
}
