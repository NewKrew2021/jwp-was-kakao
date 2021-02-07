package web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookies {

    private static final String COOKIE_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> attributes;

    private HttpCookies(Map<String, String> attributes) {
        this.attributes = Collections.unmodifiableMap(attributes);
    }

    public static HttpCookies from(String cookieAsString) {
        if (cookieAsString == null || cookieAsString.isEmpty()) {
            return new HttpCookies(new HashMap<>());
        }

        String[] cookies = cookieAsString.split(COOKIE_DELIMITER);
        Map<String, String> attributes = new HashMap<>();
        for (String cookie : cookies) {
            String[] keyValue = cookie.split(KEY_VALUE_DELIMITER);
            attributes.put(keyValue[KEY].trim(), keyValue[VALUE].trim());
        }

        return new HttpCookies(attributes);
    }

    public static HttpCookies of(String key, String value) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(key, value);
        return new HttpCookies(attributes);
    }

    public String get(String key) {
        return attributes.get(key);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return attributes.entrySet().stream()
                .map(entry -> entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER + " "));
    }
}
