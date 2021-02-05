package webserver;

import web.HttpSession;

public interface SessionManager {
    HttpSession create();

    HttpSession get(String key);

}
