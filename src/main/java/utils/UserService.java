package utils;

import db.DataBase;
import model.User;

import java.util.ArrayList;
import java.util.List;
public class UserService {
    private UserService() {
    }

    public static boolean isInValidUser(String userId, String password) {
        User user = DataBase.findUserById(userId);
        return user == null || !user.getPassword().equals(password);
    }

    public static void insert(String userId, String password, String name, String email) {
        User user = User.of(userId, password, name, email);
        DataBase.addUser(user);
    }

    public static List<User> findAllUsers() {
        return new ArrayList<>(DataBase.findAll());
    }
}
