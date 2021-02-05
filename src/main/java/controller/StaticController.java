package controller;

import http.HttpResponse;
import org.springframework.http.HttpStatus;

public class StaticController {
    public static final String TEXT_CSS_CHARSET_UTF_8 = "text/css;charset=utf-8";
    public static final String STATIC = "./static";

    public static Handler staticHandler = (request) -> new HttpResponse.Builder()
            .status(HttpStatus.OK)
            .contentType(TEXT_CSS_CHARSET_UTF_8)
            .body(STATIC + request.getUri())
            .build();
}
