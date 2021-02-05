package controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {
    public static Map<String, Controller> controllerMap;

    private static void init(){
        controllerMap=new HashMap<>();
        controllerMap.put("/user/create", new CreateUserController());
        controllerMap.put("/user/list", new ListUserController());
        controllerMap.put("/user/list.html", new ListUserController());
        controllerMap.put("/user/login", new LoginController());
    }

    public static Controller get(String path){
        if(controllerMap == null){
            init();
        }
        return controllerMap.get(path);
    }
}
