package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import db.DataBase;
import webserver.model.ContentType;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;
import webserver.model.HttpStatus;

import java.io.IOException;
import java.util.Collections;

public class UserListController implements Controller {
    private static final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".html"));

    @Override
    public String getPath() {
        return "/user/list";
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        if (!Boolean.TRUE.toString().equals(request.getCookie("logined"))) {
            response.sendFound("/user/login.html");
            return;
        }

        try {
            Template template = handlebars.compile("user/list");
            String listPage = template.apply(Collections.singletonMap("userList", DataBase.findAll()));
            response.sendOk(ContentType.HTML, listPage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            response.sendHeader(HttpStatus.NOT_FOUND);
        }
    }
}
