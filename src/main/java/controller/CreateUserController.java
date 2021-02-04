package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CreateUserController extends AbstractController{

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = null;
        String email = null;
        try {
            name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
            email = URLDecoder.decode(request.getParameter("email"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        DataBase.addUser(new User(userId, password, name, email));
        response.sendRedirect("/index.html");
    }
}