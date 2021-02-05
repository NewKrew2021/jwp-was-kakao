package webserver;

import web.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private final Map<String, HttpSession> sessions;

    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    public boolean contains(String key) {
        return sessions.containsKey(key);
    }

    public void add(HttpSession httpSession) {
        sessions.put(httpSession.getId(), httpSession);
    }
}
