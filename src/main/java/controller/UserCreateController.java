package controller;

import annotation.web.RequestMethod;
import webserver.Controller;
import webserver.Model;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import model.User;
import db.DataBase;
import utils.HttpUtils;

import java.util.Map;

public class UserCreateController extends Controller {
    public UserCreateController() {
        super(RequestMethod.POST, "/user/create");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == requestMethod && request.getUri().equals(uri);
    }

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response, Model model) {
        Map<String, String> params = HttpUtils.getParamMap(request.getBody());

        User user = new User(params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
        DataBase.addUser(user);

        return "redirect:/index.html";
    }
}
