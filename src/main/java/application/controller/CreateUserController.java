package application.controller;

import application.user.UserService;
import model.User;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class CreateUserController extends AbstractController {

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        UserService.createUser(user);
        httpResponse.sendRedirect("/index.html");
    }

}
