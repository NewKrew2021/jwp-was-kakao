package controller;

import service.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserCreateController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.insert(httpRequest);
        logger.debug("create user successfully");
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
