package domain;

import webserver.*;

import java.util.HashMap;
import java.util.Map;

public class URLMapper {
    public static final String INDEX_URL = "/index.html";

    private static final String USER_CREATE_URL = "/user/create";
    private static final String USER_LOGIN_URL = "/user/login";
    private static final String USER_LIST_URL = "/user/list";

    private static final Map<String, Controller> urlToController = new HashMap<>();
    private static final FileController defaultController = new FileController();

    static {
        urlToController.put(USER_CREATE_URL, new UserCreateController());
        urlToController.put(USER_LOGIN_URL, new UserLoginController());
        urlToController.put(USER_LIST_URL, new UserListController());
    }

    public static Controller get(String url) {
        if(!urlToController.containsKey(url)) {
            return defaultController;
        }
        return urlToController.get(url);
    }


}
