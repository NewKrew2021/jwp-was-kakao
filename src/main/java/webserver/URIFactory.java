package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginUserController;

import java.util.HashMap;
import java.util.Map;

public class URIFactory {

    private static final String USER_CREATE_URL = "/user/create";
    private static final String USER_LOGIN_URL = "/user/login";
    private static final String USER_LIST_URL = "/user/list";

    private static final Map<String, Controller> CONTROLLER_MAP = new HashMap<>();

    static {
        CONTROLLER_MAP.put(USER_CREATE_URL, new CreateUserController());
        CONTROLLER_MAP.put(USER_LOGIN_URL, new LoginUserController());
        CONTROLLER_MAP.put(USER_LIST_URL, new ListUserController());
    }

    public void create(Request request, Response response) {

        if (ResponseHandler.isStaticFile(request.getURL())) {
            response.forward();

        } else if (CONTROLLER_MAP.containsKey(request.getPathGateway())) {
            Controller controller = CONTROLLER_MAP.get(request.getPathGateway());
            controller.service(request, response);

        } else {
            response.forward();
        }
    }
}
