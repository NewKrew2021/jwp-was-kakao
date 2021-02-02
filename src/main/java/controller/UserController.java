package controller;

import db.DataBase;
import domain.Dispatcher;
import domain.Request;
import domain.Response;
import model.User;

import java.util.Map;

public class UserController {
    private static final UserController instance = new UserController();
    private final Dispatcher dispatcher = Dispatcher.getInstance();

    private UserController() {
    }

    public void registerAll() {
        this.dispatcher.register("/user/create", "GET", this::createByGet);
        this.dispatcher.register("/user/create", "POST", this::createByPost);
    }

    public Response createByGet(Request request) {
        Map<String, String> quires = request.getQueries();
        DataBase.addUser(new User(
                quires.get("userId"),
                quires.get("password"),
                quires.get("name"),
                quires.get("email")
        ));
        return Response.ofRedirect("/index.html");
    }

    public Response createByPost(Request request) {
        Map<String, String> bodies = request.getBodies();
        DataBase.addUser(new User(
                bodies.get("userId"),
                bodies.get("password"),
                bodies.get("name"),
                bodies.get("email")
        ));
        return Response.ofRedirect("/index.html");
    }

    public static UserController getInstance() {
        return instance;
    }
}
