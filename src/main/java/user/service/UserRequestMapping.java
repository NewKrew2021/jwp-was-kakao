package user.service;

import user.controller.UserCreateController;
import user.controller.UserListController;
import user.controller.UserLoginController;
import user.controller.UserProfileController;
import webserver.http.Controller;
import webserver.http.ExceptionHandler;
import webserver.http.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserRequestMapping implements RequestMapping {
    private final List<Controller> controllers;

    public UserRequestMapping() {
        List<Controller> controllersToAdd = Arrays.asList(
                new UserProfileController(),
                new UserCreateController(),
                new UserListController(),
                new UserLoginController());

        controllers = controllersToAdd.stream()
                .map(ExceptionHandler::new)
                .collect(Collectors.toList());
    }

    @Override
    public Controller getController(String path) {
        return controllers.stream()
                .filter(controller -> controller.supports(path))
                .findFirst()
                .orElse(null);
    }
}
