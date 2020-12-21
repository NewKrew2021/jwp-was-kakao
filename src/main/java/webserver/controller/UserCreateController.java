package webserver.controller;

import db.DataBase;
import model.User;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;

public class UserCreateController implements Controller {
    @Override
    public String getPath() {
        return "/user/create";
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        DataBase.addUser(user);
        response.sendFound("/index.html");
    }
}
