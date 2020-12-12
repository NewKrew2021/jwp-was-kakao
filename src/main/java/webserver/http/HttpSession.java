package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    public static final String COOKIE_HTTP_SESSION_KEY = "session_key";

    private final UUID id;
    private final Map<String, Object> attributes;

    private HttpSession(UUID id, Map<String, Object> attributes) {
       this.id = id;
       this.attributes = attributes;
    }

    public HttpSession() {
        this(UUID.randomUUID(), new HashMap<>());
    }

    public static HttpSession of(UUID id, Map<String, Object> attributes) {
        return new HttpSession(id, attributes);
    }

    public UUID getId() {
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
