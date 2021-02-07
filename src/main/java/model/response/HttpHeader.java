package model.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeader {
    private final Map<String, String> headers = new HashMap<>();

    public void put(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeader() {
        return headers;
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return headers.entrySet();
    }
}
