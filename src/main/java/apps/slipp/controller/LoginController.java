package apps.slipp.controller;

import apps.slipp.model.User;
import apps.slipp.service.LoginException;
import apps.slipp.service.LoginService;
import apps.slipp.service.UserService;
import webserver.http.*;
import webserver.http.session.HttpSession;
import webserver.http.view.RedirectView;

import java.util.List;

public class LoginController implements Controller {

    private LoginService loginService = new LoginService();
    private UserService userService = new UserService();

    @Override
    public ModelAndView execute(HttpRequest request, HttpResponse response) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(request.getBody());
        Login login = new Login(params);

        try {
            loginService.login(login.getUserId(), login.getPassword());

            User user = userService.get(login.getUserId());
            HttpSession session = request.getSession();
            session.setAttribute("profile", Profile.of(user));

            response.setSetCookie("logined=true; Path=/");
            return ModelAndView.of(new RedirectView("/index.html"));
        } catch (LoginException e) {
            response.setSetCookie("logined=false; Path=/");
            return ModelAndView.of(new RedirectView("/user/login_failed.html"));
        }
    }
}
