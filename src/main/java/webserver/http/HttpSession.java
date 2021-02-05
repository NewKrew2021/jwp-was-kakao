package webserver.http;

import com.google.common.collect.Maps;
import db.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private String id;
    private Map<String, Object> attributes = new HashMap<>();

    public static final String USER = "user";
    public static final String SESSION = "session";

    public static HttpSession getNewSession() {
        HttpSession session = new HttpSession(UUID.randomUUID().toString());
        DataBase.addSession(session);
        return session;
    }

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
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
