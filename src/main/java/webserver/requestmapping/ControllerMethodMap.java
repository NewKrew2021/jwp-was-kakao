package webserver.requestmapping;

import controller.Controller;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerMethodMap {
    private final Map<HttpMethod, Method> map;
    private final Controller controller;

    public ControllerMethodMap(Controller controller, List<HttpMethod> httpMethods, List<Method> controllerMethods) {
        this.controller = controller;
        map = new HashMap<>();
        for (int i = 0; i < httpMethods.size(); i++) {
            map.put(httpMethods.get(i), controllerMethods.get(i));
        }
    }

    public Method find(HttpMethod httpMethod) {
        return map.get(httpMethod);
    }

    public Controller getController() {
        return controller;
    }
}
