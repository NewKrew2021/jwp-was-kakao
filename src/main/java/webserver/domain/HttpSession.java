package webserver.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private static final Map<String, Object> session = new HashMap<>();

    public static String getId() {
        return UUID.randomUUID().toString();
    }

    public static void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public static Object getAttribute(String name) {
        return session.get(name);
    }

    public static void removeAttribute(String name) {
        session.remove(name);
    }

    public static void invalidate() {
        session.clear();
    }
}
