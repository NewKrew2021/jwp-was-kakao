package domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpHeaders {

    private final Map<String, List<String>> headers;

    public HttpHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public List<String> getHeader(String key) {
        return headers.getOrDefault(key, Collections.emptyList());
    }

}
