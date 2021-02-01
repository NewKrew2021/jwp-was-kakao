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
    };
}
