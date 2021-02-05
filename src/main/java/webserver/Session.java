package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    private UUID uuid;
    private Map<String, Object> attributes = new HashMap<>();

    public Session(UUID uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return uuid.toString();
    }

    public void setAttributes(String name, Object object) {
        attributes.put(name, object);
    }

    public Object getAttribute(String name){
        return attributes.get(name);
    }

    public void remoteAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
