package webserver;

import com.google.common.collect.ImmutableMap;
import webserver.controller.Controller;
import webserver.http.HttpMethod;

import java.util.Arrays;
import java.util.Map;

class RequestMapping {
    private final Map<String, Controller> uriMapping;


    RequestMapping(HttpMethod method, String uri, Controller indexController) {
        this(ImmutableMap.of(toMappingURI(method, uri), indexController));
    }

    public RequestMapping(Map<String, Controller> uriMapping) {
        if (!startWithHttpMethod(uriMapping)) {
            throw new InvalidMappingURIFormatException();
        }
        this.uriMapping = uriMapping;
    }

    private boolean startWithHttpMethod(Map<String, Controller> uriMapping) {
        return uriMapping.keySet().stream().anyMatch(uri -> Arrays.stream(HttpMethod.values()).anyMatch(method -> uri.startsWith(method.name())));
    }

    public Controller getController(HttpMethod method, String uri) {
        return uriMapping.get(toMappingURI(method, uri));
    }

    static String toMappingURI(HttpMethod method, String uri) {
        return String.format("%s %s", method.name(), uri);
    }
}
