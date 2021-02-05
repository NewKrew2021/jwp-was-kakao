package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CreateUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
        String email = URLDecoder.decode(request.getParameter("email"), "UTF-8");

        if (userId == null || password == null || name == null || email == null) {
            response.badRequest();
            return;
        }
        DataBase.addUser(new User(userId, password, name, email));
        response.sendRedirect("/index.html");
    }
}
