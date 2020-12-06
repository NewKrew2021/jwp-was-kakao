package webserver;

import com.google.common.collect.ImmutableMap;
import webserver.controller.Controller;
import webserver.http.HttpMethod;

import java.util.Map;

class RequestMapping {
    private final Map<String, Controller> uriMapping;

    RequestMapping(String uri, Controller indexController) {
        this(ImmutableMap.of(uri, indexController));
    }

    RequestMapping(HttpMethod method, String uri, Controller indexController) {
        this(ImmutableMap.of(toMappingURI(method, uri), indexController));
    }

    public RequestMapping(Map<String, Controller> uriMapping) {
        this.uriMapping = uriMapping;
    }

    public Controller getController(String uri) {
        return uriMapping.get(uri);
    }

    public Controller getController(HttpMethod method, String uri) {
        return uriMapping.get(toMappingURI(method, uri));
    }

    static String toMappingURI(HttpMethod method, String uri) {
        return String.format("%s %s", method.name(), uri);
    }
}
