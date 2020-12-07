package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private final UUID id;
    private final Map<String, Object> attributes;

    public HttpSession() {
        id = UUID.randomUUID();
        attributes = new HashMap<>();
    }

    public String getId() {
        return id.toString();
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
