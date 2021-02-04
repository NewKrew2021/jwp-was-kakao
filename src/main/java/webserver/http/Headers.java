package webserver.http;

import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

public class Headers {
    private final Map<String, List<String>> headers = Maps.newHashMap();

    public void saveHeader(String headerValue) {
        String[] headerAndValue = headerValue.split(": ");
        String key = headerAndValue[0].trim().toLowerCase();
        String value = headerAndValue[1].trim().toLowerCase();

        List<String> keyValues = headers.getOrDefault(key, new LinkedList<>());
        keyValues.add(value);

        headers.put(key, keyValues);
    }

    public String get(String header) {
        return Optional.ofNullable(headers.get(header))
                .orElseGet(Collections::emptyList)
                .stream()
                .collect(Collectors.joining("; "));
    }
}
