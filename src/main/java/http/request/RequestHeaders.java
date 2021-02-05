package http.request;

import http.Cookies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeaders {
    private final Map<String, String> headers;

    public RequestHeaders(List<String> lines) {
        headers = new HashMap<>();
        for (int i = 1; !lines.get(i).equals(""); i++) {
            String[] pair = lines.get(i).split(":");
            headers.put(pair[0].trim(), pair[1].trim());
        }
    }

    public Cookies extractCookies() {
        return new Cookies(headers.get("Cookie"));
    }

    @Override
    public String toString() {
        return "RequestHeaders{" +
                "headers=" + headers +
                '}';
    }
}
