package web;

import java.util.HashMap;
import java.util.Map;

import static web.HttpSession.SESSION_ID;

public class SessionStorage {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession from(HttpRequest httpRequest) {
        HttpCookies cookie = HttpCookies.from(httpRequest.getHttpHeaders().get(HttpHeaders.COOKIE));

        String sessionId = cookie.get(SESSION_ID);
        if (sessionId == null) {
            return createNewSession();
        }
        return getSession(sessionId);
    }

    private static HttpSession createNewSession() {
        HttpSession session = HttpSession.create();
        sessions.put(session.getId(), session);
        return session;
    }

    private static HttpSession getSession(String sessionId) {
        HttpSession session = sessions.get(sessionId);
        if (isInvalidSession(session)) {
            return createNewSession();
        }
        return session;
    }

    private static boolean isInvalidSession(HttpSession session) {
        return session == null || !session.isValid();
    }
}
