package webserver.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.*;

import java.util.List;

public class LoginController implements Controller {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private LoginAuthenticator loginAuthenticator = new LoginAuthenticator();

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        Login login = new Login(params);

        try {
            loginAuthenticator.authenticate(login.getUserId(), login.getPassword());
            httpResponse.setStatus(HttpStatus.x302_Found);
            httpResponse.addHeader("Location", "/index.html");
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
        } catch (AuthenticationException e) {
            logger.debug(e.getMessage(), e.getCause());
            httpResponse.setStatus(HttpStatus.x302_Found);
            httpResponse.addHeader("Location", "/user/login_failed.html");
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
        }
    }
}
