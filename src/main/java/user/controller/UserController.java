package user.controller;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import user.model.User;

public class UserController {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
