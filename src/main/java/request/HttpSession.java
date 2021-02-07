package request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpSession {
    private final String id;

    private Map<String, Object> data;

    private HttpSession(String id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public static HttpSession from(String id) {
        return new HttpSession(id, new HashMap<>());
    }

    public void setAttribute(String name, Object value) {
        this.data.put(name, value);
    }

    public Optional<Object> getAttribute(String name) {
        if (this.data.containsKey(name)) {
            return Optional.of(this.data.get(name));
        }
        return Optional.empty();
    }

    public void removeAttribute(String name) {
        this.data.remove(name);
    }

    public void invalidate() {
        this.data.clear();
    }


}
