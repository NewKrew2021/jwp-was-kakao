package controller;

import db.DataBase;
import exception.NotDefinedMethodException;
import webserver.Request;
import webserver.Response;
import webserver.Session;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(Request request, Response response) throws Exception {
        Session session = request.getSession();
        if (session != null && (Boolean) session.getAttribute("logined")) {
            response.userListForward(request.getUri(), DataBase.findAll());
            return;
        }
        response.sendRedirect("/user/login.html");
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        throw new NotDefinedMethodException();
    }
}
