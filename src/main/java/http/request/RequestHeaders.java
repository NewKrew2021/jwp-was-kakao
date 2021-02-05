package http.request;

import java.util.HashMap;
import java.util.Map;

import static http.request.RequestParams.KEY;
import static http.request.RequestParams.VALUE;

public class RequestHeaders {

    public static final int HEADER_START_LINE = 1;

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeaders(String[] requestMessageLines) {
        for (int i = HEADER_START_LINE; i < requestMessageLines.length; i++) {
            headers.put(requestMessageLines[i].split(":")[KEY], requestMessageLines[i].split(":")[VALUE].trim());
        }
    }

    public String get(String key) {
        return headers.get(key);
    }

    public boolean containsKey(String key) {
        return headers.containsKey(key);
    }
}
