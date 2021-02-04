package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

public class CreateUserController extends AbstractController {

    public static final String INDEX_HTML = "/index.html";
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter(USER_ID), httpRequest.getParameter(PASSWORD),
                httpRequest.getParameter(NAME), httpRequest.getParameter(EMAIL));
        DataBase.addUser(user);
        httpResponse.response302Header(INDEX_HTML);
    }

}
