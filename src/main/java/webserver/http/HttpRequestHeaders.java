package http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaders {
    Map<String, HttpRequestHeader> headers;

    private HttpRequestHeaders(Map<String, HttpRequestHeader> headers) {
        this.headers = headers;
    }

    public static HttpRequestHeaders of(String headerLines) {
        Map<String, HttpRequestHeader> headers = new HashMap<>();

        for (String line : headerLines.split("\n")) {
            HttpRequestHeader header = HttpRequestHeader.of(line);
            headers.put(header.getHeaderName(), header);
        }

        return new HttpRequestHeaders(headers);
    }

    public String getHeader(String name) {
        try {
            return headers.get(name).getHeaderContent();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
