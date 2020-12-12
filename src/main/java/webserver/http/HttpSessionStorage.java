package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionStorage {
    private static final Map<UUID, HttpSession> sessions = new HashMap<>();

    public static HttpSession getSession(UUID id) {
        return sessions.get(id);
    }

    public static void putSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }
}
