package webserver.controller;

import db.DataBase;
import db.HttpSessions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ListUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        Session session = getSession(httpRequest);
        if (session != null) {
            HashMap<String, Object> users = getUsersTemplate();
            return new HttpResponse.Builder()
                    .status(HttpStatusCode.OK)
                    .contentType("text/html;charset=utf-8")
                    .body(httpRequest.getPath(), users)
                    .build();
        }
        return new HttpResponse.Builder()
                .status(HttpStatusCode.FOUND)
                .redirect("/user/login.html")
                .build();
    }

    private HashMap<String, Object> getUsersTemplate() {
        HashMap<String, Object> users = new HashMap<>();
        users.put("users", new ArrayList<>(DataBase.findAll()));
        return users;
    }

    private Session getSession(HttpRequest httpRequest) {
        if (!httpRequest.containsCookie("Session")) {
            return null;
        }
        return HttpSessions.getSession(httpRequest.getCookies().getCookie("Session").getValue());
    }
}
