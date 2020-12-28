package webserver.model;

import db.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    public static final String SESSION_COOKIE_NAME = "SESSION-ID";

    private final UUID uuid;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpSession() {
        uuid = UUID.randomUUID();
        DataBase.addSession(this);
    }

    public static HttpSession of(String sessionId) {
        return DataBase.findSessionById(sessionId);
    }

    public String getId() {
        return uuid.toString();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
        DataBase.removeSessionById(getId());
    }
}
