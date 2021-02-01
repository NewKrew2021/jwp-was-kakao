package controller;

import db.DataBase;
import domain.Dispatcher;
import domain.Request;
import model.User;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserController {
    private static final UserController instance = new UserController();
    private final Dispatcher dispatcher = Dispatcher.getInstance();

    private UserController() {
    }

    public void registerAll() {
        this.dispatcher.register("/user/create", "GET", this::create);
    }

    //Get, post
    public byte[] create(Request request) {
        Map<String, String> quires = request.getQueries();
        DataBase.addUser(new User(
                quires.get("userId"),
                quires.get("password"),
                quires.get("name"),
                quires.get("email")
        ));
        return ("TEST" + DataBase.findUserById(quires.get("userId"))).getBytes(StandardCharsets.UTF_8);
    }

    public static UserController getInstance() {
        return instance;
    }
}
