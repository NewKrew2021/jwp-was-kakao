package webserver.controller;

import db.DataBase;
import model.User;
import webserver.RequestHandler;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;

public class UserCreateController implements Controller {

    private static final String path = "/user/create";

    public String getPath() {
        return path;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);

        httpResponse.addHeader("Location", RequestHandler.BASE_URL);
        httpResponse.sendRedirect();
    }
}
