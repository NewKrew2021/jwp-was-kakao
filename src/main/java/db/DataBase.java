package db;

import com.google.common.collect.Maps;
import model.Session;
import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();
    private static Map<String, Session> sessions = Maps.newHashMap();

    public static void addUser(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new IllegalArgumentException();
        }
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        if (!users.containsKey(userId)) {
            throw new NullPointerException();
        }
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static Session addSession() {
        String id = UUID.randomUUID().toString();
        Session session = new Session(id);
        sessions.put(id, session);
        return session;
    }

    public static Session findSessionById(String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            throw new NullPointerException();
        }
        return sessions.get(sessionId);
    }
}
