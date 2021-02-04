package webserver.service;

import user.controller.UserCreateController;
import user.controller.UserListController;
import user.controller.UserLoginController;
import user.controller.UserProfileController;
import webserver.controller.DefaultController;
import webserver.controller.ForwardController;
import webserver.controller.StaticController;
import webserver.http.Controller;
import webserver.http.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManualRequestMapping implements RequestMapping {
    private static final Controller DEFAULT_CONTROLLER = new DefaultController();
    private final List<Controller> controllers;

    public ManualRequestMapping() {
        List<Controller> controllersToAdd = Arrays.asList(
                new UserProfileController(),
                new UserCreateController(),
                new UserListController(),
                new UserLoginController(),
                new ForwardController(),
                new StaticController());

        controllers = controllersToAdd.stream()
                .map(ExceptionHandler::new)
                .collect(Collectors.toList());
    }

    @Override
    public Controller getController(String path) {
        return controllers.stream()
                .filter(controller -> controller.supports(path))
                .findFirst()
                .orElse(DEFAULT_CONTROLLER);
    }
}
