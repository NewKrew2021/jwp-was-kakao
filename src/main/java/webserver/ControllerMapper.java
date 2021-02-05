package webserver;

import controller.*;
import exception.DuplicateException;

import java.util.Arrays;
import java.util.List;

public class ControllerMapper {
    private static final AbstractController defaultController = ResourceController.getInstance();

    private static ControllerMapper instance;
    private final List<AbstractController> controllers;

    private ControllerMapper(List<AbstractController> controllers) {
        validate(controllers);
        this.controllers = controllers;
    }

    public static ControllerMapper getInstance() {
        if (instance == null) {
            instance = new ControllerMapper(Arrays.asList(
                    UserCreateController.getInstance(),
                    UserLoginController.getInstance(),
                    UserListController.getInstance()));
        }
        return instance;
    }

    public Controller getController(String path) {
        return controllers.stream()
                .filter(controller -> controller.match(path))
                .findAny()
                .orElse(defaultController);
    }

    private void validate(List<AbstractController> controllers) {
        if (!allPathsAreUnique(controllers)) {
            throw new DuplicateException("중복된 경로를 갖는 컨트롤러가 존재합니다.");
        }
    }

    private boolean allPathsAreUnique(List<AbstractController> controllers) {
        return controllers.stream()
                .map(AbstractController::getPath)
                .distinct()
                .count() == controllers.size();
    }
}
