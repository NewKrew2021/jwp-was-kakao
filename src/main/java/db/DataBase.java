package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static final Map<String, User> users = Maps.newHashMap();
    static {
        DataBase.addUser(new User("kit.t", "1234", "김광수", "kit.t@kakaocorp.com"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
