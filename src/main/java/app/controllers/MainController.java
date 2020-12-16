package app.controllers;

import webserver.HttpHandler;
import webserver.HttpResponse;
import webserver.constant.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

// FIXME rename
public class MainController {

    public static HttpHandler getStaticFileHandler = (method, target, req) ->
            buildFileHttpResponse("./static" + target);

    public static HttpHandler getTemplateFileHandler = (method, target, req) ->
            buildFileHttpResponse("./templates" + target);

    private static HttpResponse buildFileHttpResponse(String resourcePath) throws IOException, URISyntaxException {
        return HttpResponse.Builder.prepare()
                .status(HttpStatus.OK)
                .file(resourcePath, true)
                .build();
    }

}
