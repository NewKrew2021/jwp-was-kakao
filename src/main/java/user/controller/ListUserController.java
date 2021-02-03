package user.controller;

import user.view.UserView;
import webserver.http.AbstractController;
import webserver.http.Body;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

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
