package web;

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

    public String get(String key) {
        return headers.get(key);
    }
}
