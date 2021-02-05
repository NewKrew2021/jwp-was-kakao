package auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionsManager {
    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession createNewSession() {
        UUID sessionId = UUID.randomUUID();
        HttpSession httpSession = HttpSession.of(sessionId);
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static boolean hasSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }
}
