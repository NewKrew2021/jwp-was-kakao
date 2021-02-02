package controller;

import org.springframework.http.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class Controllers {
    private static Map<String, Controller> controllerMap = new HashMap<>();

    public Controllers() {
        controllerMap.put("/user/create" , new CreateUserController());
        controllerMap.put("/user/login" , new LoginController());
        controllerMap.put("/user/list" , new ListUserController());
    }

    public Controller getController(String uri) {
        if(controllerMap.containsKey(uri)) {
            return controllerMap.get(uri);
        }
        return new StaticController();
    }
}
