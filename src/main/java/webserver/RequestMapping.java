package webserver;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

class RequestMapping {
    private final Map<String, Controller> uriMapping;

    RequestMapping(String uri, Controller indexController) {
        this(ImmutableMap.of(uri, indexController));
    }

    public RequestMapping(Map<String, Controller> uriMapping) {
        this.uriMapping = uriMapping;
    }

    public Controller getController(String uri) {
        return Optional.ofNullable(uriMapping.get(uri))
                .orElseThrow(() -> new ControllerNotFoundException(uri));
    }

}
