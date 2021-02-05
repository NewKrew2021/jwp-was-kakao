package webserver.model;

import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    public static final String COOKIE_DELIMITER = "=";
    public static final String COOKIE_STRING_DELIMITER = "; ";
    public static final String COOKIE_END_WITH = "; Path=/";
    public static final String COOKIE_START_WITH = "Set-Cookie: ";
    public static final String LOGINED = "logined";
    public static final String LOGINED_TRUE = "true";
    public static final String LOGINED_FALSE = "false";
    public static final String SESSION = "session";

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
        return cookieString.concat(StringUtils.NEW_LINE);
    }

    public String toString() {
        String str = "";

        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            String cookieString = StringUtils.concatThree(
                    COOKIE_START_WITH,
                    String.join(COOKIE_DELIMITER, entry.getKey(), entry.getValue()),
                    COOKIE_END_WITH
            );
            str = StringUtils.concatThree(str, cookieString, StringUtils.NEW_LINE);
        }

        return str;
    }
}
