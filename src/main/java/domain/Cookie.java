package domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private static final String COOKIE_DELIMITER = "; ";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";

    private Map<String, String> cookies = new HashMap<>();

    public Cookie(String cookieString) {
        Arrays.stream(cookieString.split(COOKIE_DELIMITER))
                    .forEach(cookie -> {
                        String[] split = cookie.split(COOKIE_KEY_VALUE_DELIMITER);
                        cookies.put(split[0], split[1]);
                    });
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cookie: ");
        for (Map.Entry<String, String> cookie : cookies.entrySet()) {
            sb.append(cookie.getKey()).append("=").append(cookie.getValue()).append("; ");
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
