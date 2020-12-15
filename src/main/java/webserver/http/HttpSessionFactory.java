package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class HttpSessionFactory {
    private final Map<String, HttpSession> sessions = new HashMap<>();

    public HttpSession getOrCreate(String sessionId) {
        String actualSessionId = sessionId;
        if (actualSessionId == null) {
            actualSessionId = UUID.randomUUID().toString();
        }
        return sessions.computeIfAbsent(actualSessionId, HttpSession::new);
    }
}
