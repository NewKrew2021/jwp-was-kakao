package webserver.http.controller;

import webserver.http.*;
import webserver.http.template.TemplateEngine;

abstract public class HandlebarsController implements Controller {

    private TemplateEngine templateEngine = TemplateEngine.handlebars();

    abstract protected Object getModelData();

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse){
        String output = templateEngine.apply(httpRequest.getPath(), getModelData());
        httpResponse.setStatus(HttpStatus.x200_OK);
        httpResponse.setContentType(ContentType.TEXT_HTML_UTF8.toString());
        httpResponse.setBody(output.getBytes());

    }
}
