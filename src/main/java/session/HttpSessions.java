package session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessions {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static void add(HttpSession httpSession) {
        sessions.put(httpSession.getId(), httpSession);
    }

    public static void remove(HttpSession httpSession) {
        sessions.remove(httpSession.getId());
    }

    public static boolean isValidateSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public static HttpSession getHttpSessionById(String sessionId) {
        return sessions.get(sessionId);
    }
}
