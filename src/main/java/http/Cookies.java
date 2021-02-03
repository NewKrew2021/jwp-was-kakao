package http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Cookies {
    private List<Cookie> cookies;

    public Cookies() {
        this.cookies = new ArrayList<>();
    }

    public Cookies(String cookies) {
        this.cookies = Arrays.stream(cookies.split("; "))
                .map(Cookie::new)
                .collect(Collectors.toList());
    }

    public void add(Cookie cookie) {
        cookies.add(cookie);
    }

    public Cookie getCookie(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.matched(name))
                .findAny()
                .orElseThrow(NullPointerException::new);
    }

    public String toSetCookies() {
        StringBuilder sb = new StringBuilder();
        cookies.forEach(cookie -> sb.append(cookie.toSetCookie()).append("\r\n"));
        return sb.toString();
    }
}
