package model;

import java.util.Map;

public class RequestHeader {
    private final Map<String, String> header;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
    }

    public String get(String key) {
        return header.get(key);
    }

}
