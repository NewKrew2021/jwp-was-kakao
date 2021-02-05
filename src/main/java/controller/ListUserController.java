package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import request.HttpRequest;
import request.HttpSessions;
import response.HttpResponse;
import utils.ParseUtils;
import webserver.WebServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.HttpHeaders.COOKIE;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String cookie = request.getHeaders().get(COOKIE.getHeader());
        if(!isLogin(ParseUtils.getSessionId(cookie))){
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

    private boolean isLogin(String sessionId) {
        return sessionId != null && HttpSessions.existHttpSession(sessionId);
    }
}
