package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUserController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            if (request.getCookie().equals("logined=true")) {

                Map<String, Object> model = new HashMap<>();

                List<User> users = new ArrayList<>(DataBase.findAll());

                model.put("users", users);

                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);

                Template template = handlebars.compile("user/list");

                String userListPage = template.apply(model);

                response.forwardBody(userListPage.getBytes(), "text/html");
            }

            response.sendRedirect("/user/login.html");
        } catch(Exception e){
            response.internalServerError();
        }
    }

}
