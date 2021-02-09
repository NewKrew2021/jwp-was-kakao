package domain;

import java.util.Map;

public class HttpParameters {

    public static final String NO_KEY = "No Key";

    private final Map<String, String> params;

    public HttpParameters(Map<String, String> params) {
        this.params = params;
    }

    public String getParameter(String key) {
        return params.getOrDefault(key, NO_KEY);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
