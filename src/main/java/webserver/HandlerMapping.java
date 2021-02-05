package webserver;

import controller.*;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static Map<String, Controller> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put("/user/create", new CreateUserController());
        handlerMapping.put("/user/list", new ListUserController());
        handlerMapping.put("/user/login", new LoginController());
        handlerMapping.put("/file", new FileController());
    }

    public static Controller getController(String url) {
        if (FileMapping.isFileRequest(url)) {
            return handlerMapping.get("/file");
        }

        return handlerMapping.get(url);
    }
}

