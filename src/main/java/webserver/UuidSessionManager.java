package webserver;

import web.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UuidSessionManager implements SessionManager {
    private final Map<String, HttpSession> sessions;

    public UuidSessionManager() {
        this.sessions = new HashMap<>();
    }

    public boolean contains(String key) {
        return sessions.containsKey(key);
    }

    @Override
    public HttpSession create() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = HttpSession.of(uuid);
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    @Override
    public HttpSession get(String key) {
        return sessions.get(key);
    }
}
