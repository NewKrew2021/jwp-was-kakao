package user.ui;

import user.model.User;
import user.service.UserService;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.ui.AbstractController;

public class LoginController extends AbstractController {
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
}
