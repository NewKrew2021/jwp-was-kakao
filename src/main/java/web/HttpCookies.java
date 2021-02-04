package web;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookies {

    private static final HttpCookies EMPTY = new HttpCookies(new HashMap<>());
    private static final String COOKIE_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final String LOGINED = "logined";
    private static final String PATH = "Path";

    private final Map<String, String> attributes;

    private HttpCookies(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public static HttpCookies from(String cookieAsString) {
        if (cookieAsString == null || cookieAsString.isEmpty()) {
            return EMPTY;
        }

        String[] cookies = cookieAsString.split(COOKIE_DELIMITER);
        Map<String, String> attributes = new HashMap<>();
        for (String cookie : cookies) {
            String[] keyValue = cookie.split(KEY_VALUE_DELIMITER);
            attributes.put(keyValue[KEY].trim(), keyValue[VALUE].trim());
        }

        return new HttpCookies(attributes);
    }

    public static HttpCookies logined() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(LOGINED, "true");
        attributes.put(PATH, "/");

        return new HttpCookies(attributes);
    }

    public static HttpCookies notLogined() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(LOGINED, "false");
        attributes.put(PATH, "/");

        return new HttpCookies(attributes);
    }

    public boolean isLogined() {
        String logined = attributes.get(LOGINED);
        return logined != null && logined.equals("true");
    }

    @Override
    public String toString() {
        return attributes.entrySet().stream()
                .map(entry -> entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER + " "));
    }
}
