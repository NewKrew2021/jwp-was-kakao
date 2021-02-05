package controller;

import com.github.jknack.handlebars.Template;
import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import exceptions.InvalidSessionException;
import model.User;
import session.HttpSessions;
import utils.HandlebarsUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUserController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            String sessionId = SessionUtils.extractSessionId(request.getCookie());

            if (HttpSessions.isValidateSession(sessionId)) {
                String userListPage = createUserListPage();

                response.forwardBody(userListPage.getBytes(), "text/html");
                return;
            }

            response.sendRedirect("/user/login.html");
        } catch (InvalidSessionException e) {
            e.printStackTrace();
            response.badRequest();
        } catch (Exception e) {
            e.printStackTrace();
            response.internalServerError();
        }
    }

    private String createUserListPage() throws IOException {
        List<User> users = new ArrayList<>(DataBase.findAll());

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Template template = HandlebarsUtils.getTemplate("user/list");

        return template.apply(model);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
