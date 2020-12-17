package app.controllers;

import db.DataBase;
import model.User;
import webserver.HttpHandler;
import webserver.HttpResponse;
import webserver.constant.HttpHeader;
import webserver.constant.HttpStatus;

public class UserController {

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

    private static HttpResponse logined() {
        String cookieValue = "logined=true; Path=/";

        return found("/index.html")
                .putHeader(HttpHeader.SET_COOKIE, cookieValue);
    }

    // TODO move to super
    public static HttpResponse found(String location) {
        return new HttpResponse(HttpStatus.FOUND)
                .putHeader(HttpHeader.LOCATION, location);
    }

}
