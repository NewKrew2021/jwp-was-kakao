package user.ui;

import user.view.UserView;
import webserver.RequestHandler;
import webserver.domain.Body;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.ui.AbstractController;

public class ListUserController extends AbstractController {

    private static final String COOKIE = "Cookie";
    private static final String TEXT_HTML = "text/html";
    private static final String INDEX_HTML = "/index.html";
    private static final String LOGINED_TRUE = "logined=true";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLogin(httpRequest.getHeader(COOKIE))) {
            httpResponse.forwardBody(new Body(UserView.getUserListHtml(), TEXT_HTML));
            return;
        }

        httpResponse.sendRedirect(INDEX_HTML);
    }

    private boolean isLogin(String cookie) {
        return cookie.contains(LOGINED_TRUE);
    }
}
