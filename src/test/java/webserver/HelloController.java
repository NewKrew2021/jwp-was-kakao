package webserver;

import webserver.constant.HttpStatus;

public class HelloController {

    public static HttpHandler getHelloHandler = req ->
            new HttpResponse(HttpStatus.OK)
                    .addHeader("X-Hello", "World!")
                    .setBody("Hello World!".getBytes());

}
