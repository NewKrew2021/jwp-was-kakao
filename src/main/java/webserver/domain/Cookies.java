package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cookies {
    private static final Logger logger = LoggerFactory.getLogger(Cookies.class);

    private Map<String, Cookie> cookies;

    public Cookies() {
        cookies = new HashMap<>();
    }

    public Cookies(String cookiesString) {
        cookies = new HashMap<>();
        Arrays.stream(cookiesString.trim().split(";")).forEach(str -> {

            String[] keyValue = str.split("=");
            logger.debug(str);
            cookies.put(keyValue[0].trim(), new Cookie(keyValue[0].trim(), keyValue[1].trim()));
        });
    }

    public Cookie getCookie(String name) {
        return cookies.get(name);
    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        cookies.forEach((key, value) -> sb.append(cookies.get(key)).append("\r\n"));
        return sb.toString();
    }
}
