package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final UUID uuid;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpSession() {
        this.uuid = UUID.randomUUID();
    }

    public String getId() {
        return uuid.toString();
    }

    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
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
