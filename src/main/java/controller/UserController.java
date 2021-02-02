package controller;

import http.HttpResponse;
import model.User;
import db.DataBase;
import utils.HttpUtils;
import utils.TemplateUtils;

import java.util.HashMap;
import java.util.Map;

public class UserController {
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

    public static Handler listUserHandler = (request) -> {
        String logined = request.getRequestHeaders().getHeader("Cookie");

        if("logined=true".equals(logined)) {
            Map<String, Object> params = new HashMap<>();
            params.put("users", DataBase.findAll());

            return new HttpResponse.Builder()
                    .setStatus("HTTP/1.1 200 OK")
                    .setHtml(TemplateUtils.buildPage(request.getUri(), params))
                    .build();
        }
        return new HttpResponse.Builder()
                .setStatus("HTTP/1.1 302 Found")
                .setRedirect("/user/login.html")
                .build();
    };
}
