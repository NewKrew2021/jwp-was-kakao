package user.controller;

import user.model.User;
import user.service.UserService;
import webserver.http.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserLoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = UserService.findById(httpRequest.getParameter("userId"));
        if (user == null || !user.same(httpRequest.getParameter("password"))) {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }

        httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    public boolean supports(String path) {
        return path.endsWith("/user/login");
    }
}
