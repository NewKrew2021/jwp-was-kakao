package webserver.controller;

import db.DataBase;
import model.User;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
        DataBase.addUser(user);
        httpResponse.sendRedirect("/index.html");
    }
}
