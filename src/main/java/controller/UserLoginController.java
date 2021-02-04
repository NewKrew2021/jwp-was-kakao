package controller;

import annotation.web.RequestMethod;
import db.DataBase;
import model.User;
import utils.HttpUtils;
import webserver.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

public class UserLoginController extends Controller {
    public UserLoginController() {
        super(RequestMethod.POST, "/user/login");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == requestMethod && request.getUri().equals(uri);
    }

    @Override
    public HttpResponse handleRequest(HttpRequest request) {
        Map<String, String> params = HttpUtils.getParamMap(request.getBody());

        User user = DataBase.findUserById(params.get("userId"));
        if (user != null && user.getPassword().equals(params.get("password"))) {
            return new HttpResponse.Builder()
                    .status("HTTP/1.1 302 Found")
                    .redirect("/index.html")
                    .header("Set-Cookie", "logined=true; Path=/")
                    .build();
        }
        return new HttpResponse.Builder()
                .status("HTTP/1.1 302 Found")
                .redirect("/user/login_failed.html")
                .header("Set-Cookie", "logined=false; Path=/")
                .build();
    }
}
