package controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {
    public static final Map<String, Controller> CONTROLLER_MAP = new HashMap<>();

    static {
        CONTROLLER_MAP.put("/user/create", new CreateUserController());
        CONTROLLER_MAP.put("/user/list", new ListUserController());
        CONTROLLER_MAP.put("/user/login", new LoginController());
    }

    public static Controller get(String path) {
        return CONTROLLER_MAP.get(path);
    }
}
