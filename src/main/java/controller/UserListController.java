package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import utils.TemplateUtils;
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

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if(!isLogin(httpRequest.getHeader("Cookie"))){
            httpResponse.sendRedirect(LOGIN_URL);
            return;
        }

        String html = TemplateUtils.makeTemplate(httpRequest.getPath());
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
}
