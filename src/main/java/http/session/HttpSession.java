package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private final String sessionId;
    private final Map<String, Object> attributes;

    public HttpSession(String sessionId) {
        this(sessionId, new HashMap<>());
    }

    public HttpSession(String sessionId, Map<String, Object> attributes) {
        this.sessionId = sessionId;
        this.attributes = attributes;
    }

    public String getId() {
        return sessionId;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void invalidate() {
        attributes.clear();
    }
}
