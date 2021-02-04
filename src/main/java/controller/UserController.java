package controller;

import annotation.web.RequestMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import model.User;
import db.DataBase;
import utils.FileIoUtils;
import utils.HttpUtils;
import utils.TemplateUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserController extends Controller {

    @Override
    public Optional<Handler> getResponsibleHandler(HttpRequest request) {
        if (request.getRequestMethod() == RequestMethod.POST && request.getUri().equals("/user/create")) {
            return Optional.of(createUserHandler);
        }
        if (request.getRequestMethod() == RequestMethod.POST && request.getUri().equals("/user/login")) {
            return Optional.of(loginUserHandler);
        }
        if (request.getRequestMethod() == RequestMethod.GET && request.getUri().equals("/user/list")) {
            return Optional.of(listUserHandler);
        }
        return Optional.empty();
    }

    private Handler createUserHandler = (request) -> {
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
    };

    private Handler loginUserHandler = (request) -> {
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
    };

    private Handler listUserHandler = (request) -> {
        String logined = request.getRequestHeaders().getHeader("Cookie");

        if ("logined=true".equals(logined)) {
            Map<String, Object> params = new HashMap<>();
            params.put("users", DataBase.findAll());

            return new HttpResponse.Builder()
                    .status("HTTP/1.1 200 OK")
                    .body(TemplateUtils.buildPage(request.getUri(), params),
                            FileIoUtils.getMimeType("./templates/index.html")
                    )
                    .build();
        }
        return new HttpResponse.Builder()
                .status("HTTP/1.1 302 Found")
                .redirect("/user/login.html")
                .build();
    };

}
