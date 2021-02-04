package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import utils.FileIoUtils;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;
import webserver.model.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController implements Controller {

    private static final String path = "/user/list";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();

        if (httpRequest.getCookie("logined") == null || httpRequest.getCookie("logined").equals("false")) {


            String body = null;
            try {
                body = FileIoUtils.loadFileStringFromClasspath("./templates" + "/user/login.html");
            } catch (IOException e) {
            } catch (URISyntaxException e) {
            }

            httpResponse.setStatus(HttpStatus.OK);
            httpResponse.addHeader("Content-Type", "text/html; charset=utf-8");
            httpResponse.addHeader("Content-Length", String.valueOf(body.length()));
            httpResponse.addHeader("Content-Location", "/user/login.html");
            httpResponse.setBody(body);

            return httpResponse;
        }


        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template;
        String profilePage = null;
        try {
            template = handlebars.compile("user/list");

            profilePage = template.apply(DataBase.findAll());
        } catch (IOException e) {

        }

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.addHeader("Content-Type", findContentType(path) + "; charset=utf-8");
        httpResponse.addHeader("Content-Length", String.valueOf(profilePage.length()));
        httpResponse.addHeader("Content-Location", path);
        httpResponse.setBody(profilePage);

        return httpResponse;
    }

    private String findContentType(String path) {
        if (path.startsWith("/css")) {
            return "text/css";
        }

        return "text/html";
    }

}
