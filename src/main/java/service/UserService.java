package service;

import db.DataBase;
import model.User;

import java.util.Collection;
import java.util.Map;

public class UserService {
    public static final String LOGINED_KEY = "logined";
    public static final String LOGINED_VALUE = "true";

    public static void addNewUser(User user) {
        DataBase.addUser(user);
    }

    public static boolean isLoginSuccessful(String userId, String password) {
        User user = DataBase.findUserById(userId);

        if (user == null) {
            return false;
        }

        return user.getPassword().equals(password);
    }

    public static Collection<User> getAllUsers() {
        return DataBase.findAll();
    }

    public static boolean isLogined(Map<String, String> cookie) {
        if (!cookie.containsKey(LOGINED_KEY)) {
            return false;
        }
        return LOGINED_VALUE.equals(cookie.get(LOGINED_KEY));
    }
}
