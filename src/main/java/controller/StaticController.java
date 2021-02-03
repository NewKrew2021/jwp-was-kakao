package controller;

import http.HttpResponse;

public class StaticController {
    public static Handler cssHandler = (request) -> new HttpResponse.Builder()
            .status("HTTP/1.1 200 OK")
            .contentType("text/css;charset=utf-8")
            .body("./static" + request.getUri())
            .build();
}
