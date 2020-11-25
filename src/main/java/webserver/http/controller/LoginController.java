package webserver.http.controller;

import webserver.http.*;

import java.util.List;

public class LoginController implements Controller{

    private LoginAuthenticator loginAuthenticator = new LoginAuthenticator();

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        Login login = new Login(params);

        if (loginAuthenticator.authenticate(login.getUserId(), login.getPassword())) {
            httpResponse.setStatus(HttpStatus.x302_Found);
            httpResponse.addHeader("Location", "/index.html");
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
        } else {
            httpResponse.setStatus(HttpStatus.x302_Found);
            httpResponse.addHeader("Location", "/user/login_failed.html");
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
        }
    }
}
