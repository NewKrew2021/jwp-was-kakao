package app.controllers;

import webserver.HttpHandler;

public class StaticFileController extends BaseController {

    public static HttpHandler getStaticFileHandler = req ->
            file("./static" + req.getTarget());

    public static HttpHandler getStaticTemplateFileHandler = req ->
            file("./templates" + req.getTarget());
}
