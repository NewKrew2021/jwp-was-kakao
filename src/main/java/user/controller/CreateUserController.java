package user.controller;

import user.service.UserService;
import user.vo.UserCreateValue;
import webserver.controller.AbstractController;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.insert(new UserCreateValue(httpRequest));
        httpResponse.sendRedirect("/index.html");
    }
}
