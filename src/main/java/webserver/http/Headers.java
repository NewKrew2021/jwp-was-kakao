package webserver.http;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Headers {
    private final Map<String, List<String>> headers = Maps.newHashMap();

    public void saveHeader(String headerValue) {
        if (headerValue.isEmpty()) {
            return;
        }

        String[] headerAndValue = headerValue.split(": ");
        saveHeader(headerAndValue[0], headerAndValue[1]);
    }

    public void saveHeader(String key, String value) {
        if (value.isEmpty()) {
            return;
        }

        String normKey = normalize(key);

        List<String> keyValues = headers.getOrDefault(normKey, new LinkedList<>());
        keyValues.add(value.trim());

        headers.put(normKey, keyValues);
    }

    public String getProcessedHeaders() {
        return headers.keySet()
                .stream()
                .map(key -> {
                    String normKey = normalize(key);
                    return String.format("%s: %s", normKey, get(normKey));
                })
                .collect(Collectors.joining("\r\n"));
    }

    public String get(String header) {
        return headers.getOrDefault(normalize(header), Collections.emptyList())
                .stream()
                .collect(Collectors.joining("; "));
    }

    private String normalize(String value) {
        return value.trim().toLowerCase();
    }
}
