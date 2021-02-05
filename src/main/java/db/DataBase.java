package db;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;

import model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static Optional<User> addUser(User user) {
        if(users.containsKey(user.getUserId())){
            return Optional.empty();
        }
        users.put(user.getUserId(), user);
        return Optional.of(user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
