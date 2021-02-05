package controller;

import db.DataBase;
import http.request.Request;
import http.response.Response;
import model.User;

public class LoginController implements Controller{
    private static final LoginController instance = new LoginController();
    private final Dispatcher dispatcher = Dispatcher.getInstance();

    private LoginController() {
    }

    public void registerAll() {
        this.dispatcher.register("/user/login", "POST", this::login);
        this.dispatcher.register("/user/logout", "GET", this::logout);
    }

    public Response login(Request request){
        User user = DataBase.findUserById(request.getBodies().get("userId"));
        if(user.getPassword().equals(request.getBodies().get("password"))){
            Response response = Response.ofRedirect("/index.html");
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            return response;
        }
        Response response = Response.ofRedirect("/user/login_failed.html");
        response.addHeader("Set-Cookie", "logined=false; Path=/");
        return response;
    }

    public Response logout(Request request){
        Response response = Response.ofRedirect("/index.html");
        response.addHeader("Set-Cookie", "logined=false; Path=/");
        return response;
    }

    public static LoginController getInstance(){
        return instance;
    }
}
