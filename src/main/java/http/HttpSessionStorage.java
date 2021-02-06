package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {
    private static final Map<String, HttpSession> httpSessions = new HashMap<>();

    public static HttpSession createHttpSession() {
        HttpSession httpSession = new HttpSession();
        httpSessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static HttpSession getHttpSession(String sessionId) {
        return httpSessions.get(sessionId);
    }
}
