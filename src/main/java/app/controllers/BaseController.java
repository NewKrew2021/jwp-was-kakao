package app.controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.HttpHandler;
import webserver.HttpResponse;
import webserver.constant.HttpHeader;
import webserver.constant.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class BaseController {

    private static final String DEFAULT_TEMPLATE_CONTENT_TYPE = "text/html; charset=utf-8";

    private static final Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        handlebars = new Handlebars(loader);
    }

    public static HttpHandler index = req -> found("/index.html");

    protected static HttpResponse found(String location) {
        return new HttpResponse(HttpStatus.FOUND)
                .putHeader(HttpHeader.LOCATION, location);
    }

    protected static HttpResponse template(String templateName, Map<String, Object> params) throws IOException {
        // TODO cache?
        Template template = handlebars.compile(templateName);

        return new HttpResponse()
                .putHeader(HttpHeader.CONTENT_TYPE, DEFAULT_TEMPLATE_CONTENT_TYPE)
                .setBody(template.apply(params).getBytes());
    }

    protected static HttpResponse file(String resourcePath) throws IOException, URISyntaxException {
        return new HttpResponse(HttpStatus.OK).setFileAsBody(resourcePath);
    }

}
