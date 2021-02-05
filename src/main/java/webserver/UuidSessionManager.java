package webserver;

import web.Cookie;
import web.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UuidSessionManager implements SessionManager {
    private final Map<String, HttpSession> sessions;

    public UuidSessionManager() {
        this.sessions = new HashMap<>();
    }

    @Override
    public boolean contains(Cookie cookie) {
        return sessions.containsKey(cookie.get("SESSIONID"));
    }

    @Override
    public HttpSession create() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = HttpSession.of(uuid);
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    @Override
    public HttpSession getByKey(String key) {
        return sessions.get(key);
    }

    @Override
    public String getSessionCookie(HttpSession httpSession) {
        return "SESSIONID=" + httpSession.getId();
    }
}
