package controller;

import db.DataBase;
import model.User;
import http.HttpRequest;
import http.HttpResponse;

public class CreateUserController extends AbstractController {

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.sendRedirect(INDEX_HTML);
    }

}
