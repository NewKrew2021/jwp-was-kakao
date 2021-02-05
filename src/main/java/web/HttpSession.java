package web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String id;
    private final Map<String, Object> attributes;

    private HttpSession(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public static HttpSession of(UUID uuid) {
        return new HttpSession(uuid.toString(), new HashMap<>());
    }

    public String getId() {
        return id;
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
