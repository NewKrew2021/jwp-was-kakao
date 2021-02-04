package user.controller;

import user.view.UserView;
import webserver.http.AbstractController;
import webserver.http.Body;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserProfileController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] html = UserView.getUserProfileHtml(httpRequest.getParameter("userId"));
        httpResponse.forwardBody(new Body(html, "text/html"));
    }

    @Override
    public boolean supports(String path) {
        return path.endsWith("/user/profile");
    }
}
