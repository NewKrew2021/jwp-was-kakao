package application.controller;

import application.user.UserService;
import model.User;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;

public class CreateUserController extends AbstractController {

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        UserService.createUser(user);
        httpResponse.response302Found("/index.html");
    }

}
