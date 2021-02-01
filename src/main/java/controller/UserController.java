package controller;

import http.HttpResponse;
import model.User;
import db.DataBase;
import utils.HttpUtils;

import java.util.Map;

public class UserController extends Controller {

    public static Handler createUserHandler = (request) -> {
        Map<String, String> params = HttpUtils.getParamMap(request.getBody());

        User user = new User(params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
        DataBase.addUser(user);

        return new HttpResponse.Builder()
                .setStatus("HTTP/1.1 302 Found")
                .setRedirect("/index.html")
                .build();
    };

    public static Handler loginUserHandler = (request) -> {
        Map<String, String> params = HttpUtils.getParamMap(request.getBody());

        User user = DataBase.findUserById(params.get("userId"));
        if (user != null && user.getPassword().equals(params.get("password"))) {
            return new HttpResponse.Builder()
                    .setStatus("HTTP/1.1 302 Found")
                    .setRedirect("/index.html")
                    .setHeader("Set-Cookie", "logined=true; Path=/")
                    .build();
        }
        return new HttpResponse.Builder()
                .setStatus("HTTP/1.1 302 Found")
                .setRedirect("/user/login_failed.html")
                .setHeader("Set-Cookie", "logined=false; Path=/")
                .build();
    };
}
