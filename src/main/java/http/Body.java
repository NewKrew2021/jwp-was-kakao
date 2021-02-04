package http;

import java.util.Map;

public class Body {
    private Map<String, String> body;

    public Body(Map<String, String> body) {
        this.body = body;
    }

    public String get(String key) {
        return body.get(key);
    }
}
