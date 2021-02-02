package utils;

import db.DataBase;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {
    private UserService() {
    }

    public static boolean isInValidUser(Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        User user = DataBase.findUserById(userId);
        return user == null || !user.getPassword().equals(password);
    }

    public static void insert(Map<String, String> params) {
        User user = User.from(params);
        DataBase.addUser(user);
    }

    public static List<User> findAllUsers() {
        return new ArrayList<>(DataBase.findAll());
    }
}
