package webserver.domain;


import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<String, Object> attribute;

    public Session() {
        this.attribute = new HashMap<>();
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public void addAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.clear();
    }
}
