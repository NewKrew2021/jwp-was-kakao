package user.controller;

import user.service.UserService;
import user.vo.UserCreateValue;
import webserver.http.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreateController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.insert(new UserCreateValue(httpRequest));
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    public boolean supports(String path) {
        return path.endsWith("/user/create");
    }
}
