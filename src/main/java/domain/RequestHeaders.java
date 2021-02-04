package domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeaders {
    private final Map<String, String> headers;

    public RequestHeaders(List<String> lines) {
        headers = new HashMap<>();
        for (int i = 1; !lines.get(i).equals(""); i++) {
            List<String >pair = Arrays.asList(lines.get(i).split(":"));
            headers.put(pair.get(0).trim(), pair.get(1).trim());
        }
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
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
