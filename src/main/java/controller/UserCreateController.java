package controller;

import utils.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserCreateController extends AbstractController {
    @Override
    public String getPath() {
        return "/user/create";
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.insert(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
        logger.debug("create user success");
        httpResponse.sendRedirect("/");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
