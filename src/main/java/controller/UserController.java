package controller;

import db.DataBase;
import domain.Dispatcher;
import domain.Request;
import domain.Response;
import domain.ResponseBody;
import model.User;

import java.util.ArrayList;

public class UserController implements Controller {
    private static final UserController instance = new UserController();
    private final Dispatcher dispatcher = Dispatcher.getInstance();

    private UserController() {
    }

    @Override
    public void registerAll() {
        this.dispatcher.register("/user/create", "GET", this::createByGet);
        this.dispatcher.register("/user/create", "POST", this::createByPost);
        this.dispatcher.register("/user/list", "GET", this::list);
    }

    public Response createByGet(Request request) {
        DataBase.addUser(new User(
                request.getQuery("userId"),
                request.getQuery("password"),
                request.getQuery("name"),
                request.getQuery("email")
        ));
        return Response.ofRedirect("/index.html");
    }

    public Response createByPost(Request request) {
        DataBase.addUser(new User(
                request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")
        ));
        return Response.ofRedirect("/index.html");
    }

    public Response list(Request request) {
        if (!request.getCookies().getValueOf("logined").equals("true")) {
            return Response.ofRedirect("/user/login.html");
        }

        ResponseBody body = ResponseBody.ofUsers(
                new ArrayList<>(DataBase.findAll())
        );
        return Response.ofDynamicHtml(body);
    }



    public static UserController getInstance() {
        return instance;
    }
}
