package webserver.controller;

import db.DataBase;
import model.User;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

public class CreateUserController extends AbstractController {
    @Override
    public HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
        DataBase.addUser(user);
        return new HttpResponse.Builder()
                .status(HttpStatusCode.FOUND)
                .redirect("/index.html")
                .build();
    }
}
