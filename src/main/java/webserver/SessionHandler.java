package webserver;

import request.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SessionHandler {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static String createSession() {
        String id = UUID.randomUUID().toString();
        sessions.put(id, HttpSession.from(id));
        return id;
    }

    public static Optional<HttpSession> getSession(String id) {
        if (sessions.containsKey(id)) {
            return Optional.of(sessions.get(id));
        }
        return Optional.empty();
    }

}
