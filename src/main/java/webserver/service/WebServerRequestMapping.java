package webserver.service;

import webserver.http.Controller;
import webserver.http.ExceptionHandler;
import webserver.http.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WebServerRequestMapping implements RequestMapping {
    private final List<Controller> controllers;

    public WebServerRequestMapping() {
        List<Controller> controllersToAdd = Arrays.asList(
                new TemplateController(),
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
                .orElse(null);
    }
}
