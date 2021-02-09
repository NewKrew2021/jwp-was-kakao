package db;

import webserver.domain.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessions {
    private static final Map<String, Session> sessions = new HashMap<>();

    public static String getId() {
        return UUID.randomUUID().toString();
    }

    public static void addSession(String name, Session session) {
        sessions.put(name, session);
    }

    public static Session getSession(String name) {
        return sessions.get(name);
    }

    public static void removeSession(String name) {
        sessions.remove(name);
    }

    public static void invalidate() {
        sessions.clear();
    }
}
