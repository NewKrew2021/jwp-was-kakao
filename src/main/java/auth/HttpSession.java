package auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String sessionId;
    private final Map<String, Object> attributes;

    private HttpSession(String sessionId, Map<String, Object> attributes) {
        this.sessionId = sessionId;
        this.attributes = attributes;
    }

    public static HttpSession of(UUID sessionId) {
        return new HttpSession(sessionId.toString(), new HashMap<>());
    }

    public String getId() {
        return sessionId;
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
    }
}
