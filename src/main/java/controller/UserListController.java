package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserListController extends AbstractController {
    private static final String DIRECT_LOGIN_URL = "http://localhost:8080/user/login.html";
    private static final String LIST_URL = "user/list";
    private static final String TEMPLATES_PREFIX = "/templates";
    private static final String HTML_SUFFIX = ".html";
    private static final String LOGIN_SUCCESS = "logined=true";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if(!isLogin(httpRequest.getHeader("Cookie"))){
            httpResponse.sendRedirect(DIRECT_LOGIN_URL);
            return;
        }

        String html = parseHtml();
        byte[] body = html.getBytes(StandardCharsets.UTF_8);
        httpResponse.forward(body);

    }

    private boolean isLogin(String loginCookie) {
        return loginCookie != null && loginCookie.equals(LOGIN_SUCCESS) ;
    }

    private String parseHtml() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PREFIX);
        loader.setSuffix(HTML_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(LIST_URL);

        return template.apply(DataBase.findAll());
    }
}
