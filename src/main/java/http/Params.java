package http;

import java.util.Map;

public class Params {
    private Map<String, String> params;

    public Params(Map<String, String> params) {
        this.params = params;
    }

    public String get(String key) {
        return params.get(key);
    }
}
