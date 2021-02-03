package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import utils.FileIoUtils;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UserListController implements Controller {

    private static final String path = "/user/list";

    public String getPath() {
        return path;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookie("logined") == null || httpRequest.getCookie("logined").equals("false")) {


            String body = null;
            try {
                body = FileIoUtils.loadFileStringFromClasspath("./templates" + "/user/login.html");
            }   catch (IOException e) {
            } catch (URISyntaxException e) {
            }

            httpResponse.addHeader("Content-Type", "text/html; charset=utf-8");
            httpResponse.addHeader("Content-Length", String.valueOf(body.length()));
            httpResponse.addHeader("Content-Location", "/user/login.html");
            httpResponse.addBody(body);
            httpResponse.forward("/user/login.html");
            return;
        }

        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/list");

            String profilePage = template.apply(DataBase.findAll());

            httpResponse.addHeader("Content-Type", findContentType(path) + "; charset=utf-8");
            httpResponse.addHeader("Content-Length", profilePage.length() + "");
            httpResponse.addHeader("Content-Location", path);
            httpResponse.addBody(profilePage);
//            httpResponse.forward(path);

            httpResponse.handleUserList(profilePage);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String findContentType(String path) {
        if (path.startsWith("/css")) {
            return "text/css";
        }

        return "text/html";
    }

}
