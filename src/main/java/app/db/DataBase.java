package app.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import app.model.User;

public class DataBase {
    private static Map<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static User findUserByIdAndPassword(String userId, String password) {
        User u = findUserById(userId);
        if (u == null) {
            return null;
        }

        if (u.getPassword().equals(password)) {
            return u;
        }

        return null;
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
