package domain;

import java.util.Map;

public class HttpHeaders {

    public static final String NO_KEY = "No Key";

    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, NO_KEY);
    }

}
