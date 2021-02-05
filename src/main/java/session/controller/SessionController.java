package session.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.model.Session;

import java.util.Map;
import java.util.UUID;

public class SessionController {
    private static final Logger log = LoggerFactory.getLogger(SessionController.class);
    private static final Map<UUID, Session> sessions = Maps.newHashMap();

    public static Session createSession() {
        UUID id = UUID.randomUUID();
        Session session = new Session(id);
        log.debug("new session {}", session);
        sessions.put(id, session);
        return session;
    }

    public static Session findSession(UUID id) {
        log.debug("find session {}", id);
        return sessions.get(id);
    }

    public static void invalidateSession(UUID id) {
        log.debug("invalidating session {}", id);
//        sessions.get(id).invalidate();
        sessions.remove(id);
    }
}
