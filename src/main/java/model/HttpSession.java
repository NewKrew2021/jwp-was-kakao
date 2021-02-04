package model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final UUID sessionId;
    private final Map<String, Object> session = new HashMap<>();

    public HttpSession(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getId() {
        return sessionId;
    }

    public Object getAttribute(String attribute) {
        return session.get(attribute);
    }

    public void setAttribute(String attribute, Object value) {
        session.put(attribute, value);
    }

    public void removeAttribute(String attribute) {
        session.remove(attribute);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "sessionId=" + sessionId +
                ", session=" + session +
                '}';
    }
}
