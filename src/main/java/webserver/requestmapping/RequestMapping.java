package webserver.requestmapping;


import controller.Controller;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

import static context.ApplicationContext.userController;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public enum RequestMapping {
    USER_CREATE_BY_GET(GET, "/user/create", userController, "addUser"),
    USER_CREATE_BY_POST(POST, "/user/create", userController, "addUser"),
    USER_LOGIN(POST, "/user/login", userController, "login"),
    USER_LIST(GET, "/user/list", userController, "showUsers");;

    private final HttpMethod httpMethod;
    private final String requestPath;
    private final Controller controller;
    private final Method method;

    RequestMapping(HttpMethod httpMethod, String requestPath, Controller controller, String methodName) {
        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.controller = controller;
        this.method = getMethodFromController(controller.getClass(), methodName);
    }

    private static Method getMethodFromController(Class<?> clazz, String name) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .orElseThrow(RequestMappingMethodNotFoundException::new);
    }

    public static Controller findController(String path) {
        return Arrays.stream(values())
                .filter(requestMapping -> requestMapping.requestPath.equals(path))
                .map(requestMapping -> requestMapping.controller)
                .findFirst()
                .orElse(null);
    }

    public static Method findMethod(String path, HttpMethod httpMethod) {
        return Arrays.stream(values())
                .filter(requestMapping -> requestMapping.requestPath.equals(path) && requestMapping.httpMethod.equals(httpMethod))
                .map(requestMapping -> requestMapping.method)
                .findFirst()
                .orElse(null);
    }
}
