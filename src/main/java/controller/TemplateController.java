package controller;

import http.HttpResponse;

public class TemplateController extends Controller {

    public static Handler htmlHandler = (request) -> new HttpResponse.Builder()
            .setStatus("HTTP/1.1 200 OK")
            .setPage("./templates" + request.getUri())
            .build();
}
