package web;

import utils.IOUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    private static final int SPLIT_SIZE = 2;
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final String DELIMITER = ": ";

    private final Map<String, String> headers;

    private HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders of(List<String> texts) {
        Map<String, String> headers = new HashMap<>();
        for (String text : texts) {
            String[] keyAndValue = text.split(DELIMITER, SPLIT_SIZE);
            headers.put(keyAndValue[KEY], keyAndValue[VALUE]);
        }
        return new HttpHeaders(headers);
    }

    public static HttpHeaders empty() {
        return new HttpHeaders(new HashMap<>());
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public String get(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(DELIMITER).append(entry.getValue()).append(IOUtils.NEW_LINE);
        }
        return sb.toString();
    }
}
