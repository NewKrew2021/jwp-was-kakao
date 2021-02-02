package user.ui;

import user.view.UserView;
import webserver.domain.Body;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.ui.AbstractController;

public class UserProfileController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] html = UserView.getUserProfileHtml(httpRequest.getParameter("userId"));
        httpResponse.forwardBody(new Body(html, "text/html"));
    }
}
