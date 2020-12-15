package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String id;
    private final Map<String, Object> map = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
        if (id == null) {
            throw new IllegalArgumentException();
        }
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        map.put(name, value);
    }

    public Object getAttribute(String name) {
        return map.get(name);
    }

    public void removeAttribute(String name) {
        map.remove(name);
    }

    public void invalidate() {
        map.clear();
    }
}
