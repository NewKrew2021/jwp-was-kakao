package service.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import service.controller.AbstractController;
import service.db.DataBase;
import service.model.User;
import framework.request.HttpRequest;
import framework.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static framework.common.HttpHeaders.COOKIE;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if(!isLogin(request.getHeaders().get(COOKIE.getHeader()))){
            response.sendRedirect("/user/login.html");
            return;
        }

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        Map<String, List> parameters = new HashMap<>();
        List<User> users = new ArrayList<>(DataBase.findAll());
        parameters.put("users", users);

        String profilePage = template.apply(parameters);

        response.responseBody(profilePage.getBytes());
    }

    private boolean isLogin(String cookie) {
        return cookie != null && cookie.contains("logined=true");
    }
}
