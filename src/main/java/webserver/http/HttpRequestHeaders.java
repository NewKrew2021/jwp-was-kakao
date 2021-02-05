package webserver.http;

import java.util.Arrays;
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

    public String getCookie(String name) {
        String cookies = getHeader("Cookie");
        if(cookies == null) {
            return null;
        }
        return Arrays.stream(cookies.split(";"))
                .map(it -> it.split("=", 2))
                .filter(it -> it[0].trim().equals(name))
                .map(it -> it[1])
                .findFirst()
                .orElse(null);
    }
}
