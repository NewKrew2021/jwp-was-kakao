package user.controller;

import user.view.UserView;
import webserver.controller.AbstractController;
import webserver.domain.Body;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public class UserProfileController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] html = UserView.getUserProfileHtml(httpRequest.getParameter("userId"));
        httpResponse.forwardBody(new Body(html, "text/html"));
    }
}
