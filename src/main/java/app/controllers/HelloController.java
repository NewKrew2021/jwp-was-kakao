package app.controllers;

import webserver.HttpHandler;
import webserver.HttpResponse;
import webserver.constant.HttpStatus;

public class HelloController {

    public static HttpHandler getHelloHandler = (method, target, req) ->
            HttpResponse.Builder.prepare()
                    .status(HttpStatus.OK)
                    .header("X-Hello", "World!")
                    .body("Hello World!".getBytes())
                    .build();

}
