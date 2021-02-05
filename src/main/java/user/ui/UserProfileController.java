package user.ui;

import user.view.UserView;
import webserver.domain.Body;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.SessionStorage;
import webserver.ui.AbstractController;

public class UserProfileController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        httpResponse.forwardBody(new Body(UserView.getUserProfileHtml(httpRequest.getParameter("userId")), "text/html"));
    }
}
