package webserver.http;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {

    private static final String COOKIE_PREFIX = "Set-Cookie: ";
    private static final String NEW_LINE = "\r\n";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String COOKIE_SEPARATOR = ";";

    private final Map<String, String> cookies;

    private Cookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Cookies() {
        this.cookies = new HashMap<>();
    }

    public static Cookies parse(String cookieStr) {
        if (StringUtils.isEmpty(cookieStr)) {
            return new Cookies();
        }

        Map<String, String> cookies = Arrays.stream(cookieStr.split(COOKIE_SEPARATOR))
                .map(String::trim)
                .map(cookie -> cookie.split(KEY_VALUE_SEPARATOR))
                .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1], (cookie1, cookie2) -> cookie1));

        return new Cookies(cookies);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public void add(String key, String value) {
        cookies.put(key, value);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    @Override
    public String toString() {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue() + ";" + "Path=/")
                .collect(Collectors.joining(NEW_LINE));
    }
}
