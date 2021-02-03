package webserver.model;

import utils.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private static final String COOKIE_DELIMITER = "=";
    private static final String COOKIE_STRING_DELIMITER = "; ";
    private static final String NEW_LINE = "\r\n";
    private static final String COOKIE_END_WITH = "; Path=/";
    private static final String COOKIE_START_WITH = "Set-Cookie: ";

    private Map<String, String> cookies = new HashMap<>();

    public Cookie() {

    }

    public Cookie(String cookieString) {
        for (String cookie : cookieString.split(COOKIE_STRING_DELIMITER)) {
            cookies.put(cookie.split(COOKIE_DELIMITER)[0], cookie.split(COOKIE_DELIMITER)[1]);
        }
    }

    public void add(String key, String value) {
        cookies.put(key, value);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public String toString(String key) {
        if (!cookies.containsKey(key)) {
            return null;
        }

        String cookieString = StringUtils.concatThree(
                    COOKIE_START_WITH,
                    String.join(COOKIE_DELIMITER, key, cookies.get(key)),
                    COOKIE_END_WITH
            );
        return cookieString.concat(NEW_LINE);
    }

    public String toString() {
        String str = "";

        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            String cookieString = StringUtils.concatThree(
                    COOKIE_START_WITH,
                    String.join(COOKIE_DELIMITER, entry.getKey(), entry.getValue()),
                    COOKIE_END_WITH
            );
            str = StringUtils.concatThree(str, cookieString, NEW_LINE);
        }

        return str;
    }
}
