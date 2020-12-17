package app.controllers;

import db.DataBase;
import model.User;
import webserver.HttpHandler;
import webserver.HttpResponse;
import webserver.constant.HttpHeader;

import java.util.HashMap;
import java.util.Map;

public class UserController extends BaseController {

    public static HttpHandler postSignUpHandler = (method, target, req) -> {
        String userId = req.getRequestParam("userId");
        String password = req.getRequestParam("password");
        String name = req.getRequestParam("name");
        String email = req.getRequestParam("email");

        // TODO validate params

        DataBase.addUser(new User(userId, password, name, email));

        return logined();
    };

    public static HttpHandler postLoginHandler = (method, target, req) -> {
        String userId = req.getRequestParam("userId");
        String password = req.getRequestParam("password");

        // TODO validate params

        User user = DataBase.findUserByIdAndPassword(userId, password);
        if (user == null) {
            return found("/user/login_failed.html");
        }

        return logined();
    };

    public static HttpHandler getListHandler = (method, target, req) -> {
        Map<String, Object> params = new HashMap<>();
        params.put("users", DataBase.findAll());

        return template("user/list", params);
    };

    private static HttpResponse logined() {
        String cookieValue = "logined=true; Path=/";

        return found("/index.html")
                .putHeader(HttpHeader.SET_COOKIE, cookieValue);
    }

}
