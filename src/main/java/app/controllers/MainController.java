package app.controllers;

import webserver.HttpHandler;
import webserver.HttpResponse;
import webserver.constant.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

// FIXME rename
public class MainController {

    public static HttpHandler getStaticFileHandler = (method, target, req) ->
            file("./static" + target);

    public static HttpHandler getTemplateFileHandler = (method, target, req) ->
            file("./templates" + target);

    // TODO move to super
    private static HttpResponse file(String resourcePath) throws IOException, URISyntaxException {
        return new HttpResponse(HttpStatus.OK).setFileAsBody(resourcePath, true, true);
    }

}
