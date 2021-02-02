package controller;

import http.HttpResponse;

public class StaticController {
    public static Handler cssHandler = (request) -> new HttpResponse.Builder()
            .setStatus("HTTP/1.1 200 OK")
            .setCss("./static" + request.getUri())
            .build();
}
