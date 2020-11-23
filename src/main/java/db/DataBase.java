package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static final Map<String, User> users = Maps.newHashMap();
    static {
        DataBase.addUser(new User("yellow", "1234", "노랑이", "yellow@color.com"));
        DataBase.addUser(new User("blue", "1234", "파랑이", "blue@color.com"));
        DataBase.addUser(new User("white", "1234", "하양이", "white@color.com"));
        DataBase.addUser(new User("green", "1234", "초록이", "green@color.com"));
        DataBase.addUser(new User("black", "1234", "까망이", "black@color.com"));
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
