package model;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private final Map<String, String> header;

    public ResponseHeader() {
        this.header = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public String get(String key) {
        return header.get(key);
    }

    @Override
    public String toString() {
        StringBuilder headerString = new StringBuilder();
        for (String key : header.keySet()) {
            headerString.append(key + ": " + header.get(key) + "\r\n");
        }
        return headerString.toString();
    }
}
