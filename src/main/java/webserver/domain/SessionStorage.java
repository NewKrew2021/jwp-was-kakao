package webserver.domain;

import com.google.common.collect.Maps;

import java.util.Map;

public class SessionStorage {
    private static final String SESSION_ID = "session_id";
    private static final String EQUAL = "=";
    private static final String QUERY_DELIMITER = "; ";
    private static final String PATH = "Path=/";
    private static final String LOGINED = "logined";
    private Map<String, HttpSession> sessions = Maps.newHashMap();

    public void addSession(HttpSession httpSession) {
        sessions.put(httpSession.getId().toString(), httpSession);
    }

    public HttpSession findSessionById(String sessionId) {
        return sessions.get(sessionId);
    }

    public void addSession(HttpSession httpSession, String value) {
        httpSession.setAttribute(LOGINED, value);
        addSession(httpSession);
    }

    public String getSessionInfo(HttpSession httpSession) {
        return SESSION_ID + EQUAL + httpSession.getId() + QUERY_DELIMITER + PATH;
    }
}
