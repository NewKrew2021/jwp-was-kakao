package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        if (users.containsKey(userId)) {
            return Optional.of(users.get(userId));
        }
        return Optional.empty();
    }

    public static Collection<User> findAll() {
        return users.values();
    }

}
