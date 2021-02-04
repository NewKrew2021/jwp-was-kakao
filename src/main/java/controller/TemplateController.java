package controller;

import http.HttpResponse;
import org.springframework.http.HttpStatus;

public class TemplateController {
    public static final String TEMPLATES = "./templates";

    public static Handler htmlHandler = (request) -> new HttpResponse.Builder()
            .status(HttpStatus.OK)
            .body(TEMPLATES + request.getUri())
            .build();

    public static Handler faviconHandler = (request) -> new HttpResponse.Builder()
            .status(HttpStatus.OK)
            .body(TEMPLATES + request.getUri())
            .build();
}
