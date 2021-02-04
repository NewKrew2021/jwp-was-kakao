package webserver.controller;

import db.DataBase;
import model.User;
import webserver.RequestHandler;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;
import webserver.model.HttpStatus;

public class UserCreateController implements Controller {

    private static final String path = "/user/create";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
        DataBase.addUser(user);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.addHeader("Location", RequestHandler.BASE_URL);

        return httpResponse;
    }
}
