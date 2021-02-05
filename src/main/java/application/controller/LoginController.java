package application.controller;

import application.user.UserService;
import org.springframework.http.HttpHeaders;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;

public class LoginController extends AbstractController {
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        boolean isLogin = UserService.login(httpRequest.getParameter("userId"), httpRequest.getParameter("password"));
        httpResponse.addHeader(HttpHeaders.SET_COOKIE, "logined=" + isLogin);

        if (isLogin) {
            httpResponse.response302Found("/index.html");
            return;
        }

        httpResponse.response302Found("/user/login_failed.html");
    }
}
