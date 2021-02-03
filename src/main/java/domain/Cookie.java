package domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private Map<String, String> cookies = new HashMap<>();

    public Cookie(String cookieString) {
        Arrays.stream(cookieString.split("; "))
                    .forEach(cookie -> cookies.put(cookie.split("=")[0], cookie.split("=")[1]));
    }

    public String get(String key) {
        return cookies.get(key);
    }
}
