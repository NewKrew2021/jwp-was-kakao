package web;

import java.util.HashMap;
import java.util.Map;

public class LoginCookie {
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final int SPLIT_SIZE = 2;
    private static final String EMPTY_STRING = "";

    private final Map<String, String> cookieValue;

    private LoginCookie(Map<String, String> cookieValue) {
        this.cookieValue = cookieValue;
    }

    public static LoginCookie of(HttpRequest httpRequest) {
        String cookie = httpRequest.getHttpHeaders().get("Cookie");
        String[] cookies = cookie.split(";");
        return new LoginCookie(toValueMap(cookies));
    }

    private static Map<String, String> toValueMap(String[] cookies) {
        Map<String, String> cookieValue = new HashMap<>();
        for (String cookie : cookies) {
            String[] keyAndValue = cookie.split("=", SPLIT_SIZE);
            cookieValue.put(keyAndValue[KEY].trim(), keyAndValue[VALUE].trim());
        }
        return cookieValue;
    }

    public String get(String key) {
        if (cookieValue.containsKey(key)) {
            return cookieValue.get(key);
        }
        return EMPTY_STRING;
    }
}
