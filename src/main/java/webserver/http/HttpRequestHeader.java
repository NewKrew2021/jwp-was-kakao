package webserver.http;

import java.util.Map;

public class HttpRequestHeader {

    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> headers;

    public HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getBodySize() {

        if (hasBody()) {
            return Integer.parseInt(headers.get(CONTENT_LENGTH));
        }

        return 0;
    }

    public boolean hasBody() {
        return headers.containsKey(CONTENT_LENGTH);
    }

    public boolean hasHeader(String key) {
        return headers.containsKey(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
