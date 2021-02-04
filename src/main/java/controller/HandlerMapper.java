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
    public Optional<Controller> findController(HttpRequest request) {
        return list.stream()
                .filter(it -> it.canHandle(request))
                .findFirst();
    }
}
