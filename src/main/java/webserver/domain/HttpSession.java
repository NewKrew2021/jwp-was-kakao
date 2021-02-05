package webserver.domain;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private UUID id;
    private Map<String, Object> attributes = Maps.newHashMap();

    public HttpSession() {
        id = UUID.randomUUID();
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
        attributes.clear();;
    }

    public boolean contains(String name) {
        return attributes.containsKey(name);
    }

}
