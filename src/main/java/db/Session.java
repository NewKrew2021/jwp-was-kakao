package db;

import com.google.common.collect.Maps;
import model.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class Session {
    private static final Logger log = LoggerFactory.getLogger(Session.class);
    private static final Map<UUID, HttpSession> sessionStorage = Maps.newHashMap();

    public static HttpSession newSession() {
        UUID sessionId = UUID.randomUUID();
        HttpSession session = new HttpSession(sessionId);
        log.debug("new session {}", session);
        sessionStorage.put(sessionId, session);
        return session;
    }

    public static HttpSession findSession(UUID sessionId) {
        log.debug("find session {}", sessionId);
        HttpSession session = sessionStorage.get(sessionId);
        return session;
    }

    public static void invalidateSession(UUID sessionId) {
        log.debug("invalidating session {}", sessionId);
        sessionStorage.remove(sessionId);
    }

    public static boolean exists(UUID sessionId) {
        log.debug("check session {} {}", sessionId, sessionStorage.get(sessionId));
        return sessionStorage.get(sessionId) != null;
    }
}
