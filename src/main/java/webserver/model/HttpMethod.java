package webserver.model;


import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");

    private String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public static HttpMethod of(String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(HttpMethod -> HttpMethod.value.equals(method))
                .findFirst()
                .orElse(null);
    }
}
