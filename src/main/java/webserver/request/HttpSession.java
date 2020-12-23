package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String id;

    private Map<String, Object> data;

    private HttpSession(String id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    public static HttpSession of(String id) {
        return new HttpSession(id, new HashMap<>());
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        this.data.put(name, value);
    }

    public Object getAttribute(String name) {
        if (this.data.containsKey(name)) {
            return this.data.get(name);
        }
        return null;
    }

    public void removeAttribute(String name) {
        this.data.remove(name);
    }

    public void invalidate() {
        this.data.clear();
    }
}
