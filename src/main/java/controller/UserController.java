package controller;

import model.User;
import db.DataBase;
import utils.HttpUtils;

import java.util.Map;

public class UserController extends Controller {

    public static RequestHandler createUserHandler = (request, dos) -> {
        Map<String, String> params = HttpUtils.getParamMap(request.getBody());

        User user = new User(params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
        DataBase.addUser(user);
        response302Header(dos, "/index.html");
    };

    public static RequestHandler loginUserHandler = (request, dos) -> {
        Map<String, String> params = HttpUtils.getParamMap(request.getBody());

        User user = DataBase.findUserById(params.get("userId"));
        if (user != null && user.getPassword().equals(params.get("password"))) {
            response302Header(dos, "/index.html", true);
            return;
        }
        response302Header(dos, "/user/login_failed.html", false);
    };
}
