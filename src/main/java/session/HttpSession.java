package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String sessionId;
    private final Map<String, Object> session = new HashMap<>();

    public HttpSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
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
