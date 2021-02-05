package user.ui;

import db.DataBase;
import webserver.domain.Cookies;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpSession;
import webserver.ui.AbstractController;

public class LogoutController extends AbstractController {

    private static final String SESSION_ID = "session_id";
    private static final String COOKIE = "Cookie";
    private static final String INDEX_PAGE = "/index.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Cookies cookies = new Cookies(httpRequest.getHeader(COOKIE));
        HttpSession session = DataBase.findSessionById(cookies.get(SESSION_ID).getValue());

        if (session != null) {
            session.invalidate();
            DataBase.addSession(session);
        }
        httpResponse.sendRedirect(INDEX_PAGE);
    }
}
