package service.controller;

import framework.common.TemplateEngine;
import framework.request.HttpRequest;
import framework.response.HttpResponse;
import service.db.DataBase;
import service.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static framework.common.HttpHeaders.COOKIE;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        if (!isLogin(request.getHeaders().get(COOKIE.getHeader()))) {
            response.sendRedirect("/user/login.html");
            return;
        }

        Map<String, List> parameters = new HashMap<>();
        List<User> users = new ArrayList<>(DataBase.findAll());
        parameters.put("users", users);

        String profilePage = TemplateEngine.apply("user/list", parameters);
        response.responseBody(profilePage.getBytes());
    }

    private boolean isLogin(String cookie) {
        return cookie != null && cookie.contains("logined=true");
    }
}
