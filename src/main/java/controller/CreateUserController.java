package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

import java.util.Map;

public class CreateUserController extends AbstractController{

    public static final String INDEX_HTML = "/index.html";
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> body = httpRequest.getBody();
        User user = new User(body.get(USER_ID), body.get(PASSWORD), body.get(NAME), body.get(EMAIL));
        DataBase.addUser(user);
        httpResponse.response302Header(INDEX_HTML);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> params = httpRequest.getParams();
        User user = new User(params.get(USER_ID), params.get(PASSWORD), params.get(NAME), params.get(EMAIL));
        DataBase.addUser(user);
        httpResponse.response302Header(INDEX_HTML);
    }

}
