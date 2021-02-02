package web;

import utils.IOUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers;

    private HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders of(List<String> texts) {
        Map<String, String> headers = new HashMap<>();
        for (String text : texts) {
            String[] keyAndValue = text.split(": ", 2);
            headers.put(keyAndValue[0], keyAndValue[1]);
        }
        return new HttpHeaders(headers);
    }

    public static HttpHeaders empty() {
        return new HttpHeaders(new HashMap<>());
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public String get(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(IOUtils.NEW_LINE);
        }
        return sb.toString();
    }
}
