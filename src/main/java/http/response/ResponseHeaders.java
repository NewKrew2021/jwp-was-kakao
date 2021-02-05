package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {

    private final Map<String, String> headers = new HashMap<>();

    public ResponseHeaders() {
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }

    public Iterable<? extends Map.Entry<String, String>> entrySet() {
        return headers.entrySet();
    }
}
