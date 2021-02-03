package controller;

import http.HttpResponse;

public class TemplateController {
    public static Handler htmlHandler = (request) -> new HttpResponse.Builder()
            .status("HTTP/1.1 200 OK")
            .body("./templates" + request.getUri())
            .build();

    public static Handler faviconHandler = (request) -> new HttpResponse.Builder()
            .status("HTTP/1.1 200 OK")
            .body("./templates" + request.getUri())
            .build();
}
