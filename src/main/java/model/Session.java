package model;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private String id;
    private Map<String, Object> attributes;

    public Session(String id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        if (!attributes.containsKey(name)) {
            throw new NullPointerException();
        }
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
