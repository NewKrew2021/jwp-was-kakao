package webserver;

import webserver.constant.HttpStatus;

public class HelloController {

    public static HttpHandler getHelloHandler = (method, target, req) ->
            new HttpResponse(HttpStatus.OK)
                    .putHeader("X-Hello", "World!")
                    .setBody("Hello World!".getBytes());

}
