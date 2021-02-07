package web;

import exception.InvalidSessionException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    public static final String SESSION_ID = "session-id";

    private final String id;
    private final Map<String, Object> attributes;
    private boolean valid;

    private HttpSession(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
        this.valid = true;
    }

    public static HttpSession create() {
        return new HttpSession(UUID.randomUUID().toString(), new HashMap<>());
    }

    public String getId() {
        checkIsValid();

        return id;
    }

    public void setAttribute(String name, Object value) {
        checkIsValid();

        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        checkIsValid();

        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        checkIsValid();

        attributes.remove(name);
    }

    public void invalidate() {
        checkIsValid();

        valid = false;
        attributes.clear();
    }

    public boolean isValid() {
        return valid;
    }

    private void checkIsValid() {
        if (!valid) {
            throw new InvalidSessionException();
        }
    }
}
