package framework.request;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {

    GET, POST;

    private static final Map<String, HttpMethod> mappings = new HashMap<>();

    static {
        for (HttpMethod httpMethod : values()) {
            mappings.put(httpMethod.name(), httpMethod);
        }
    }

    public static HttpMethod of(String method) {
        return mappings.get(method.toUpperCase());
    }

}
