package service;

import db.DataBase;
import model.User;
import request.HttpRequest;

public class CreateUserService {
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    public void createUser(HttpRequest httpRequest){
        User user = new User(httpRequest.getParameter(USER_ID), httpRequest.getParameter(PASSWORD),
                httpRequest.getParameter(NAME), httpRequest.getParameter(EMAIL));
        DataBase.addUser(user);
    }
}
