package app.controllers;

import webserver.HttpHandler;

public class StaticFileController extends BaseController {

    public static HttpHandler getStaticFileHandler = (method, target, req) ->
            file("./static" + target);

    public static HttpHandler getStaticTemplateFileHandler = (method, target, req) ->
            file("./templates" + target);
}
