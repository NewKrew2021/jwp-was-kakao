package app.controllers;

import app.db.DataBase;
import app.model.User;
import webserver.Cookie;
import webserver.HttpHandler;
import webserver.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class UserController extends BaseController {

    public static HttpHandler postSignUpHandler = req -> {
        String userId = req.getRequestParam("userId");
        String password = req.getRequestParam("password");
        String name = req.getRequestParam("name");
        String email = req.getRequestParam("email");

        // TODO validate params

        DataBase.addUser(new User(userId, password, name, email));

        return logined();
    };

    public static HttpHandler postLoginHandler = req -> {
        String userId = req.getRequestParam("userId");
        String password = req.getRequestParam("password");

        // TODO validate params

        User user = DataBase.findUserByIdAndPassword(userId, password);
        if (user == null) {
            return found("/user/login_failed.html");
        }

        return logined();
    };

    public static HttpHandler getListHandler = req -> {
        String logined = req.getCookie().getValueOrDefault("logined", "");

        if (!logined.equalsIgnoreCase("true")) {
            return found("/user/login.html");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("users", DataBase.findAll());

        return template("user/list", params);
    };

    private static HttpResponse logined() {
        return found("/index.html")
                .putCookie(new Cookie()
                        .putValue("logined", "true")
                        .putAttribute("Path=/"));
    }

}
