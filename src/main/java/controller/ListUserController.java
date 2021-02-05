package controller;

import db.DataBase;
import exception.NotDefinedMethodException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import http.HttpSessionStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class ListUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        throw new NotDefinedMethodException();
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String httpSessionId = request.getCookie("sessionId").getValue();
        HttpSession httpSession = httpSessionId == null ?
                HttpSessionStorage.createHttpSession() : HttpSessionStorage.getHttpSession(httpSessionId);

        if (httpSession != null && (Boolean) httpSession.getAttribute("logined")) {
            response.userListForward(request.getUri(), DataBase.findAll());
            return;
        }
        response.sendRedirect("/user/login.html");
    }
}
