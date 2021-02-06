package controller;

import db.DataBase;
import exception.NotDefinedMethodException;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = User.of(request.getParameters());
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        throw new NotDefinedMethodException();
    }
}
