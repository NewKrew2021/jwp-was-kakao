package apps.slipp.controller;

import apps.slipp.service.LoginException;
import apps.slipp.service.LoginService;
import webserver.http.*;
import webserver.http.view.RedirectView;

import java.util.List;

public class LoginController implements Controller {

    private LoginService loginService = new LoginService();

    @Override
    public ModelAndView execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        Login login = new Login(params);

        try {
            loginService.login(login.getUserId(), login.getPassword());
            httpResponse.setCookie("logined=true; Path=/");
            return ModelAndView.of(new RedirectView("/index.html"));
        } catch (LoginException e) {
            httpResponse.setCookie("logined=false; Path=/");
            return ModelAndView.of(new RedirectView("/user/login_failed.html"));
        }
    }
}
