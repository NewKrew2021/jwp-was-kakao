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
        this.dispatcher.register("/user/create", "GET", this::createByGet);
        this.dispatcher.register("/user/create", "POST", this::createByPost);
    }

    //Get, post
    public byte[] createByGet(Request request) {
        Map<String, String> quires = request.getQueries();
        DataBase.addUser(new User(
                quires.get("userId"),
                quires.get("password"),
                quires.get("name"),
                quires.get("email")
        ));
        return ("HTTP/1.1 302 Found\n" +
                "Location: http://localhost/index.html").getBytes();
       // return ("TEST" + DataBase.findUserById(quires.get("userId"))).getBytes(StandardCharsets.UTF_8);
    }

    public byte[] createByPost(Request request) {
        Map<String, String> bodies = request.getBodies();
        DataBase.addUser(new User(
                bodies.get("userId"),
                bodies.get("password"),
                bodies.get("name"),
                bodies.get("email")
        ));
        return ("TEST" + DataBase.findUserById(bodies.get("userId"))).getBytes(StandardCharsets.UTF_8);
    }

    public static UserController getInstance() {
        return instance;
    }
}
