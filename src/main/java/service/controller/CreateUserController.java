package service.controller;

import service.db.DataBase;
import service.model.User;
import framework.request.HttpRequest;
import framework.response.HttpResponse;

import java.io.IOException;

public class CreateUserController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = User.mapOf(request.getParameters());
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
