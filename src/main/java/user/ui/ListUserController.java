package user.ui;

import db.DataBase;
import user.view.UserView;
import webserver.RequestHandler;
import webserver.domain.*;
import webserver.ui.AbstractController;

import java.util.Arrays;

public class ListUserController extends AbstractController {

    private static final String COOKIE = "Cookie";
    private static final String TEXT_HTML = "text/html";
    private static final String INDEX_HTML = "/index.html";
    private static final String SESSION_ID = "session_id";
    private static final String LOGINED = "logined";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        if (isLogin(httpRequest.getHeader(COOKIE), sessionStorage)) {
            httpResponse.forwardBody(new Body(UserView.getUserListHtml(), TEXT_HTML));
            return;
        }

        httpResponse.sendRedirect(INDEX_HTML);
    }

    private boolean isLogin(String cookie, SessionStorage sessionStorage) {
        Cookies cookies = new Cookies(cookie);

        try {
            HttpSession session = sessionStorage.findSessionById(cookies.get(SESSION_ID).getValue());

            if (session != null) {
                return session.contains(LOGINED);
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
