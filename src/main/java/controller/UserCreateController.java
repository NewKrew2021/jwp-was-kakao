package controller;

import annotation.web.RequestMethod;
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
    public HttpResponse handleRequest(HttpRequest request) {
        Map<String, String> params = HttpUtils.getParamMap(request.getBody());

        User user = new User(params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
        DataBase.addUser(user);

        return new HttpResponse.Builder()
                .status("HTTP/1.1 302 Found")
                .redirect("/index.html")
                .build();
    }
}
