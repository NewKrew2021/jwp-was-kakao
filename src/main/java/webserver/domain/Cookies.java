package webserver.domain;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Cookies {
    private List<Cookie> cookies = Lists.newArrayList();

    public Cookies(String cookies) {
        Arrays.stream(cookies.split(";"))
                .map(String::trim)
                .forEach(cookie -> {
                    String[] token = cookie.split("=");
                    this.cookies.add(new Cookie(token[0], token[1]));
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