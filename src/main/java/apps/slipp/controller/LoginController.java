package apps.slipp.controller;

import apps.slipp.service.LoginException;
import apps.slipp.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.*;

import java.util.List;

public class LoginController implements Controller {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private LoginService loginService = new LoginService();

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        Login login = new Login(params);

        try {
            loginService.login(login.getUserId(), login.getPassword());
            httpResponse.setCookie("logined=true; Path=/");
            httpResponse.sendRedirect("/index.html");
        } catch (LoginException e) {
            httpResponse.setCookie("logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }
}
