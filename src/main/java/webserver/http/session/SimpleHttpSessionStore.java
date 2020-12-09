package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class SimpleHttpSessionStore implements HttpSessionStore {

    private Map<SessionId, HttpSession> store;

    public SimpleHttpSessionStore() {
        store = new HashMap<>();
    }

    @Override
    public HttpSession getSession(SessionId sessionId) {
        return store.get(sessionId);
    }

    @Override
    public HttpSession saveSession(HttpSession session) {
        try {
            store.put(session.getId(), session);
            return session;
        } catch (RuntimeException e) {
            throw new HttpSessionStoreException("session 저장에 실패했습니다", e);
        }
    }

    @Override
    public HttpSession removeSession(SessionId sessionId) {
        try {
            return store.remove(sessionId);
        } catch (RuntimeException e) {
            throw new HttpSessionStoreException("session 삭제에 실패했습니다", e);
        }
    }

    @Override
    public boolean contains(SessionId sessionId) {
        return store.containsKey(sessionId);
    }
}
