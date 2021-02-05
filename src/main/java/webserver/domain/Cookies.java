package webserver.domain;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Cookies {
    private static final String COOKIE_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private List<Cookie> cookies = Lists.newArrayList();

    public Cookies(String cookies) {
        Arrays.stream(cookies.split(COOKIE_DELIMITER))
                .map(String::trim)
                .forEach(cookie -> {
                    String[] token = cookie.split(KEY_VALUE_DELIMITER);
                    this.cookies.add(new Cookie(token[KEY_INDEX], token[VALUE_INDEX]));
                });
    }

    public void add(Cookie cookie) {
        cookies.add(cookie);
    }

    public Cookie get(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.match(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}