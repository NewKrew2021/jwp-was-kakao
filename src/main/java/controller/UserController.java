package controller;

import model.User;
import db.DataBase;

public class UserController extends Controller {

    public static RequestHandler createUserHandler = (request, dos) -> {
        User user = new User(request.getParam("userId"),
                request.getParam("password"),
                request.getParam("name"),
                request.getParam("email"));
        DataBase.addUser(user);
    };
}
