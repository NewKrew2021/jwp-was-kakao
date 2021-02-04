package application.controller;

import application.user.UserService;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import webserver.domain.ContentTypes;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.isLogined()) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }

        try {
            byte[] body = makeUserListBody(httpRequest.getPath());
            httpResponse.addHeader(CONTENT_TYPE, ContentTypes.HTML.getType());
            httpResponse.response200Header(body.length);
            httpResponse.responseBody(body);
        } catch (IOException e) {
            e.printStackTrace();
            httpResponse.response404();
        }

    }


    private byte[] makeUserListBody(String path) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);
        List<User> users = new ArrayList<>(UserService.getAllUser());
        return template.apply(users).getBytes(StandardCharsets.UTF_8);
    }

}
