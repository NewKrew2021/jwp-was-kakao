package user.ui;

import db.DataBase;
import user.model.User;
import user.service.UserService;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpSession;
import webserver.ui.AbstractController;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = UserService.findById(httpRequest.getParameter("userId"));
        HttpSession httpSession = new HttpSession();

        String sessionInfo = "session_id=" + httpSession.getId() + "; Path=/";
        if (user == null || !user.same(httpRequest.getParameter("password"))) {
            httpSession.setAttribute("logined", "false");
            DataBase.addSession(httpSession);

            httpResponse.addHeader("Set-Cookie", sessionInfo);
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }

        httpSession.setAttribute("logined", "true");
        DataBase.addSession(httpSession);
        httpResponse.addHeader("Set-Cookie", sessionInfo);

        httpResponse.sendRedirect("/index.html");
    }
}
