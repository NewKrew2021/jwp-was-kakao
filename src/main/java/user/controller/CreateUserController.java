package user.controller;

import user.service.UserService;
import user.vo.UserCreateValue;
import webserver.http.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.insert(new UserCreateValue(httpRequest));
        httpResponse.sendRedirect("/index.html");
    }
}
