package annotation.web;

import java.util.Arrays;

public enum RequestMethod {
    GET("GET"), HEAD("HEAD"), POST("POST"), PUT("PUT"), PATCH("PATCH"), DELETE("DELETE"), OPTIONS("OPTIONS"), TRACE("TRACE"), NOTHING("NOTHING");

    private String method;

    RequestMethod(String method) {
        this.method = method;
    }

    public static RequestMethod getMethod(String line) {
        String[] parsedLine = line.split(" ");
        return Arrays.stream(values())
                .filter(it -> parsedLine[0].equals(it.method))
                .findAny()
                .orElse(RequestMethod.NOTHING);
    }
}
