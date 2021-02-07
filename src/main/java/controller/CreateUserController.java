package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CreateUserController extends AbstractController {

    private CreateUserController() {
    }

    public static CreateUserController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final CreateUserController INSTANCE = new CreateUserController();
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
    }

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
