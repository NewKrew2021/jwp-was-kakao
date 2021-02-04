package controller;

import db.DataBase;
import model.User;
import webserver.Request;
import webserver.Response;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(Request request, Response response) throws Exception {
        User user = User.of(request.getParameters());
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }

    @Override
    public void doGet(Request request, Response response) throws Exception {
        User user = User.of(request.getParameters());
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
