package annotation.web;

import exception.HttpException;
import org.springframework.http.HttpStatus;

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

    public static RequestMethod of(String method) throws HttpException {
        return Arrays.stream(RequestMethod.values())
                .filter(requestMethod -> requestMethod.value.equals(method))
                .findFirst()
                .orElseThrow(() -> new HttpException(HttpStatus.METHOD_NOT_ALLOWED));
    }
}
