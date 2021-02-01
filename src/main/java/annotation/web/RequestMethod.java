package annotation.web;

import java.util.Arrays;

public enum RequestMethod {

    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");

    private String value;

    RequestMethod(String value) {
        this.value = value;
    }

    public static RequestMethod of(String method) {
        return Arrays.stream(RequestMethod.values())
                .filter(requestMethod -> requestMethod.value.equals(method))
                .findFirst()
                .orElse(null);
    }
}
