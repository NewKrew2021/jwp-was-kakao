package domain;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {
    private final Map<String, String> headers;

    public ResponseHeaders() {
        this.headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        return headers.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue() + "\n")
                .reduce("", (sum, entry) -> sum + entry);
    }
}
