package request;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private String id;
    private Map<String, Object> attributes;

    private HttpSession() {
        this.id = UUID.randomUUID().toString();
        this.attributes = new HashMap<>();
    }

    public static HttpSession of(){
        return new HttpSession();
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
         attributes.put(name,value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
