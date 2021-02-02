package user.ui;

import user.view.UserView;
import webserver.RequestHandler;
import webserver.domain.Body;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.ui.AbstractController;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLogin(httpRequest.getHeader("Cookie"))) {
            httpResponse.forwardBody(new Body(UserView.getUserListHtml(), "text/html"));
            return;
        }

        httpResponse.sendRedirect("/index.html");
    }

    private boolean isLogin(String cookie) {
        return cookie.contains("logined=true");
    }
}
