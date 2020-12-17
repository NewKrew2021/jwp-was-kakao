package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionRegistry {
    private Map<String, HttpSession> sessionMap = new HashMap<>();

    public boolean hasSession(String id) {
        return sessionMap.containsKey(id);
    }

    public String createSession() {
        String id = UUID.randomUUID().toString();
        sessionMap.put(id, HttpSession.of(id));
        return id;
    }

    public HttpSession getSession(String id) {
        if (hasSession(id)) {
            return sessionMap.get(id);
        }
        return null;
    }
}
