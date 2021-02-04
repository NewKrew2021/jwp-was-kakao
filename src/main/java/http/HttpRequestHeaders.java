package http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaders {
    Map<String, String> headers;

    private HttpRequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequestHeaders of(String headerLines) {
        Map<String, String> headers = new HashMap<>();

        for (String line : headerLines.split("\n")) {
            String[] parsedLine = line.split(" ", 2);
            String name = parsedLine[0].replace(":", "");
            String content = parsedLine[1];
            headers.put(name, content);
        }

        return new HttpRequestHeaders(headers);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
}
