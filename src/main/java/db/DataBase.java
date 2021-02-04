package db;

import com.google.common.collect.Maps;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static final Logger log = LoggerFactory.getLogger(DataBase.class);
    private static final Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        log.debug("add user {}", user);
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        log.debug("find user {}", userId);
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        log.debug("get All users");
        return users.values();
    }
}
