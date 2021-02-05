package controller;

import com.github.jknack.handlebars.Template;
import db.DataBase;
import exceptions.InvalidSessionException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSessions;
import model.User;
import utils.HandlebarsUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUserController extends AbstractController {

    private ListUserController() {
    }

    public static ListUserController getInstance() {
        return ListUserController.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final ListUserController INSTANCE = new ListUserController();
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            String sessionId = SessionUtils.extractSessionId(request.getCookie());

            if (HttpSessions.isValidateSession(sessionId)) {
                String userListPage = createUserListPage();

                response.setBody(userListPage.getBytes(), "text/html");
                response.ok();
                return;
            }

            response.sendRedirect("/user/login.html");
        } catch (InvalidSessionException e) {
            e.printStackTrace();
            response.sendRedirect("/user/login.html");
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
