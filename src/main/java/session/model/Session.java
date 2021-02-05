package session.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    private final UUID id;
    private final Map<String, Object> session = new HashMap<>();

    public Session(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
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

    public void invalidate() {
        session.clear();
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "sessionId=" + id +
                ", session=" + session +
                '}';
    }
}
