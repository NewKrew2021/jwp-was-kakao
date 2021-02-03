package controller;

import db.DataBase;
import http.Cookie;
import http.Cookies;
import http.HttpResponse;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    public static Handler createUserHandler = (request) -> {
        Map<String, String> params = request.getBody();

        User user = new User(params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
        DataBase.addUser(user);

        return new HttpResponse.Builder()
                .status("HTTP/1.1 302 Found")
                .redirect("/index.html")
                .build();
    };

    public static Handler loginUserHandler = (request) -> {
        Map<String, String> params = request.getBody();

        User user = DataBase.findUserById(params.get("userId"));
        if (user != null && user.getPassword().equals(params.get("password"))) {
            Cookie cookie = new Cookie("logined", "true");
            cookie.setPath("/");

            return new HttpResponse.Builder()
                    .status("HTTP/1.1 302 Found")
                    .redirect("/index.html")
                    .cookie(cookie)
                    .build();
        }
        return new HttpResponse.Builder()
                .status("HTTP/1.1 302 Found")
                .redirect("/user/login_failed.html")
                .build();
    };

    public static Handler listUserHandler = (request) -> {
        try {
            Cookies cookies = request.getCookies();
            cookies.getCookie("logined");

            Map<String, Object> params = new HashMap<>();
            params.put("users", DataBase.findAll());

            return new HttpResponse.Builder()
                    .status("HTTP/1.1 200 OK")
                    .contentType("text/html;charset=utf-8")
                    .body(request.getUri(), params)
                    .build();
        } catch (NullPointerException e) {
            return new HttpResponse.Builder()
                    .status("HTTP/1.1 302 Found")
                    .redirect("/user/login.html")
                    .build();
        }
    };
}
