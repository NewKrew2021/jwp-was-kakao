package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateUserController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        User user = User.mapOf(request.getBodies());
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
