package webserver;

import com.google.common.collect.ImmutableMap;
import webserver.controller.Controller;

import java.util.Map;

class RequestMapping {
    private final Map<String, Controller> uriMapping;

    RequestMapping(String uri, Controller indexController) {
        this(ImmutableMap.of(uri, indexController));
    }

    public RequestMapping(Map<String, Controller> uriMapping) {
        this.uriMapping = uriMapping;
    }

    public Controller getController(String uri) {
        return uriMapping.get(uri);
    }

}
