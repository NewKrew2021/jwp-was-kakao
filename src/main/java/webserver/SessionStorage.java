package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStorage {
    private static Map<String, Session> sessions = new HashMap<>();

    public static Session createSession() {
        Session session = new Session(UUID.randomUUID());
        sessions.put(session.getId(), session);
        return session;
    }

    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
