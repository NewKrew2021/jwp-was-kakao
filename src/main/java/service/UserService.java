package service;

import db.DataBase;
import model.User;
import webserver.HttpRequest;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserService() {
    }

    public static void insert(HttpRequest httpRequest) {
        User user = User.of(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
        DataBase.addUser(user);
    }

    public static boolean isValidUser(HttpRequest httpRequest) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        User user = DataBase.findUserById(userId);
        return user == null || !user.isCorrectPassword(password);
    }

    public static List<User> findAllUsers() {
        return new ArrayList<>(DataBase.findAll());
    }
}
