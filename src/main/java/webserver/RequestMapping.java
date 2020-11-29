package webserver;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Objects;

class RequestMapping {
    private final Map<String, Controller> uriMapping;

    public RequestMapping(String uri, Controller indexController) {
        this(ImmutableMap.of(uri, indexController));
    }

    public RequestMapping(Map<String, Controller> uriMapping) {

        this.uriMapping = uriMapping;
    }

    public Controller getController(String uri) {
        Controller controller = uriMapping.get(uri);
        if (Objects.isNull(controller)) {
            throw new ControllerNotFoundException(uri);
        }
        return controller;
    }

}
