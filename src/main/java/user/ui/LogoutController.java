package user.ui;

import webserver.domain.*;
import webserver.ui.AbstractController;

public class LogoutController extends AbstractController {

    private static final String SESSION_ID = "session_id";
    private static final String COOKIE = "Cookie";
    private static final String INDEX_PAGE = "/index.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        Cookies cookies = new Cookies(httpRequest.getHeader(COOKIE));
        HttpSession session = sessionStorage.findSessionById(cookies.get(SESSION_ID).getValue());

        if (session != null) {
            session.invalidate();
            sessionStorage.addSession(session);
        }
        httpResponse.sendRedirect(INDEX_PAGE);
    }
}
