package controller;

import db.DataBase;
import model.User;
import webserver.Request;
import webserver.Response;

public class CreateUserController extends AbstractController {
    @Override
    public void service(Request request, Response response) throws Exception {
        if (request.getMethod().equals("GET")) {
            doGet(request, response);
        }
        if (request.getMethod().equals("POST")) {
            doPost(request, response);
        }
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        User user = User.of(request.getParameters());
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
