package dto.request;

import exceptions.InvalidRequestMethodException;

import java.util.HashMap;
import java.util.Map;

public class RequestMethod {

    private static final Map<String, RequestMethod> httpRequestMethods = new HashMap<>();

    static {
        httpRequestMethods.put("GET", new RequestMethod("GET"));
        httpRequestMethods.put("POST", new RequestMethod("POST"));
        httpRequestMethods.put("DELETE", new RequestMethod("DELETE"));
        httpRequestMethods.put("PUT", new RequestMethod("PUT"));
        httpRequestMethods.put("PATCH", new RequestMethod("PATCH"));
        httpRequestMethods.put("HEAD", new RequestMethod("HEAD"));
        httpRequestMethods.put("OPTIONS", new RequestMethod("OPTIONS"));
    }

    private final String method;

    private RequestMethod(String method) {
        this.method = method;
    }

    public static RequestMethod of(String method) {
        validate(method);
        return httpRequestMethods.get(method);
    }

    private static void validate(String method) {
        if (method == null || method.length() == 0 || !httpRequestMethods.containsKey(method)) {
            throw new InvalidRequestMethodException(method);
        }
    }

    public String getMethod() {
        return method;
    }
}
