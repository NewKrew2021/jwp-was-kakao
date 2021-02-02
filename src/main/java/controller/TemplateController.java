package controller;

import http.HttpResponse;

public class TemplateController {
    public static Handler htmlHandler = (request) -> new HttpResponse.Builder()
            .setStatus("HTTP/1.1 200 OK")
            .setHtml("./templates" + request.getUri())
            .build();

    public static Handler faviconHandler = (request) -> new HttpResponse.Builder()
            .setStatus("HTTP/1.1 200 OK")
            .setCss("./templates" + request.getUri())
            .build();
}
