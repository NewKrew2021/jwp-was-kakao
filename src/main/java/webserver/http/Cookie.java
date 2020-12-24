package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private final Map<String, String> cookies;

    public Cookie() {
        cookies = new HashMap<>();
    }

    public Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

}
