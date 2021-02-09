package session.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.model.Session;

import java.util.Map;

public class SessionController {
    private static final Logger log = LoggerFactory.getLogger(SessionController.class);
    private static final Map<String, Session> sessions = Maps.newHashMap();

    public static Session createSession(String userId) {
        Session session = new Session();
        session.setAttribute(Session.USER_ID, userId);
        log.debug("new session {}", session);
        sessions.put(session.getId(), session);
        return session;
    }

    public static Session findSession(String id) {
        log.debug("find session {}", id);
        return sessions.get(id);
    }

    public static void invalidateSession(String id) {
        log.debug("invalidating session {}", id);
        sessions.get(id).invalidate();
    }
}
