package controller;

import webserver.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HandlerMapper {
    private List<Controller> list = new ArrayList<>();

    public void addController(Controller controller) {
        list.add(controller);
    }
    public Optional<Handler> findHandler(HttpRequest request) {
        return list.stream()
                .map(it -> it.getResponsibleHandler(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
