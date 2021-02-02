package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import webserver.domain.HttpHeader;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!isLogin(httpRequest.getHeaders().get(HttpHeader.COOKIE))) {
            httpResponse.getHeaders().add(HttpHeader.LOCATION, "/user/login.html");
            httpResponse.send(HttpStatusCode.FOUND);
            return;
        }
        try {
            httpResponse.send(HttpStatusCode.OK, getProfilePage());
        } catch (IOException e) {
            httpResponse.send(HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String getProfilePage() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("user/list");

        Map<String, List<User>> users = new HashMap();
        users.put("users", new ArrayList<>(DataBase.findAll()));
        return template.apply(users);
    }

    private boolean isLogin(String loginCookie) {
        if (loginCookie == null) {
            return false;
        }
        return loginCookie.contains("logined=true");
    }
}
