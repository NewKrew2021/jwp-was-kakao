package app.controllers;

import webserver.HttpHandler;
import webserver.HttpResponse;
import webserver.constant.HttpStatus;

public class HelloController {

    public static HttpHandler getHelloHandler = (method, target, req) ->
            new HttpResponse(HttpStatus.OK)
                    .putHeader("X-Hello", "World!")
                    .setBody("Hello World!".getBytes(), true);

}
