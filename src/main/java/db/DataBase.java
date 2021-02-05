package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import user.model.User;
import webserver.domain.HttpSession;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();
    private static Map<String, HttpSession> sessions = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void addSession(HttpSession httpSession) {
        sessions.put(httpSession.getId().toString(), httpSession);
    }

    public static HttpSession findSessionById(String sessionId) {
        return sessions.get(sessionId);
    }
}
