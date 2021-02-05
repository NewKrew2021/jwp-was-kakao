package webserver;

import web.Cookie;
import web.HttpSession;

public interface SessionManager {
    HttpSession create();

    boolean contains(Cookie cookie);

    HttpSession getByKey(String key);

    String getSessionCookie(HttpSession httpSession);
}
