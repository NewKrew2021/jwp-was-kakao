package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String id;
    private Map<String, Object> attributes = new HashMap<>();

    public HttpSession(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }
    public String getId(){
        return this.id;
    }
    public void setAttribute(String name, Object value){
        attributes.put(name, value);
    }
    public Object getAttribute(String name){
        return attributes.get(name);
    }
    public void removeAttribute(String name){
        attributes.remove(name);
    }
    public void invalidate(){
        attributes.clear();
    }
}
