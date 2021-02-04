package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CreateUserController extends AbstractController{

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = null;
        String email = null;

        name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
        email = URLDecoder.decode(request.getParameter("email"), "UTF-8");

        if(userId == null || password == null || name == null || email == null){
            response.badRequest();
        }
        DataBase.addUser(new User(userId, password, name, email));
        response.sendRedirect("http://" + request.getHost() + "/index.html");
    }
}
