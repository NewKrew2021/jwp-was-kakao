package model;

import java.util.Map;

public class HttpSession {
    private String sessionId;
    private Map<String, Object> objects;

    public HttpSession(String sessionId, String userId) {
        sessionId = this.sessionId;
        objects.put("userId", userId);
    }

    public String getId() {
        return sessionId;
    }

    void setAttribute(String name, Object value) {
        objects.put(name, value);
    }

    Object getAttribute(String name) {
        return objects.get(name);
    }

    void removeAttribute(String name) {
        objects.remove(name);
    }

    void invalidate() {

    }
}
