package controller;

import http.HttpRequest;

import java.util.Arrays;
import java.util.Optional;

public class HandlerMapper {
    public static Controller[] list = new Controller[] {
            new StaticController(),
            new TemplateController(),
            new UserController()
    };

    public static Optional<Handler> findHandler(HttpRequest request) {
        return Arrays.stream(list)
                .map(it -> it.getResponsibleHandler(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

}
