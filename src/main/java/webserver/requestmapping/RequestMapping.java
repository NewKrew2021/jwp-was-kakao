package webserver.requestmapping;


import controller.Controller;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static context.ApplicationContext.loginController;
import static context.ApplicationContext.userController;

public class RequestMapping {
    private static final Map<String, ControllerMethodMap> map;

    static {
        map = new HashMap<>();
        map.put("/user/create", getMethodMap(userController,
                Arrays.asList(HttpMethod.POST, HttpMethod.GET),
                Arrays.asList("addUser", "addUser")));
        map.put("/user/login", getMethodMap(loginController,
                Collections.singletonList(HttpMethod.POST),
                Collections.singletonList("login")));
        map.put("/user/list", getMethodMap(userController,
                Collections.singletonList(HttpMethod.GET),
                Collections.singletonList("showUsers")));
    }

    private static ControllerMethodMap getMethodMap(Controller controller, List<HttpMethod> httpMethods, List<String> methodNames) {
        return new ControllerMethodMap(controller, httpMethods, getMethods(controller.getClass(), methodNames));
    }

    private static List<Method> getMethods(Class<?> clazz, List<String> methodNames) {
        return methodNames.stream()
                .map(name -> getMethod(clazz, name))
                .collect(Collectors.toList());
    }

    private static Method getMethod(Class<?> clazz, String name) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static Controller findController(String path) {
        ControllerMethodMap controllerMethodMap = map.get(path);
        if (Optional.ofNullable(controllerMethodMap).isPresent()) {
            return controllerMethodMap.getController();
        }
        return null;
    }

    public static Method findMethod(String path, HttpMethod httpMethod) {
        ControllerMethodMap controllerMethodMap = map.get(path);
        if (Optional.ofNullable(controllerMethodMap).isPresent()) {
            return controllerMethodMap.find(httpMethod);
        }
        return null;
    }
}
