package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final String LOGIN_URL = "/user/login.html";
    private static final String LIST_URL = "/user/list";
    private static final String TEMPLATES_PREFIX = "/templates";
    private static final String HTML_SUFFIX = ".html";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if(!isLogin(httpRequest.getHeader("Cookie"))){
            httpResponse.sendRedirect(LOGIN_URL);
        }

        String html = parseHtml();
        byte[] body = html.getBytes(StandardCharsets.UTF_8);
        httpResponse.forward(body);

    }

    private boolean isLogin(String loginCookie) {
        try{
            return Boolean.parseBoolean(loginCookie.split("=")[1]);
        } catch (NullPointerException e){
            return false;
        }
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
