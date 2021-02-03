package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;
import session.HttpSession;
import session.HttpSessions;
import utils.SessionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUserController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            String sessionId = SessionUtils.extractSessionId(request.getCookie());
            HttpSession httpSession = HttpSessions.getHttpSessionById(sessionId);

            if (httpSession != null) {
                List<User> users = new ArrayList<>(DataBase.findAll());

                Map<String, Object> model = new HashMap<>();
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
        } catch (Exception e) {
            e.printStackTrace();
            response.internalServerError();
        }
    }

}
