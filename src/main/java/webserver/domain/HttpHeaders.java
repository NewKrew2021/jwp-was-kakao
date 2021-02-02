package webserver.domain;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers = new HashMap<>();

    public void add(HttpHeader key, String value) {
        add(key.getMessage(), value);
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public String get(HttpHeader key) {
        return get(key.getMessage());
    }

    public String get(String key) {
        return headers.get(key);
    }

    public boolean contain(HttpHeader key) {
        return contain(key.getMessage());
    }

    public boolean contain(String key) {
        return headers.containsKey(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
