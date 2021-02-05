package service.controller;

import service.controller.AbstractController;
import service.db.DataBase;
import service.model.User;
import framework.request.HttpRequest;
import framework.response.HttpResponse;

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
