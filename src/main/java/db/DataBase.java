package db;

import com.google.common.collect.Maps;
import user.exceptions.IllegalUserValuesException;
import user.model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static final Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        if (users.get(user.getUserId()) != null) {
            throw new IllegalUserValuesException(String.format("User with userId: %s already exists", user.getUserId()));
        }

        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
