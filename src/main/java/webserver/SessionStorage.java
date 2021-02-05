package webserver;

import java.util.HashMap;
import java.util.Map;

public class SessionStorage {
    public static Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getSession() {
        HttpSession session = new HttpSession();
        sessions.put(session.getId(), session);
        return session;
    }

    public static HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
