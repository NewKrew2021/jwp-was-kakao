package webserver.http.session;

import java.util.Optional;

public class SimpleHttpSessionManager implements HttpSessionManager {

    private HttpSessionStore sessionStore;

    public SimpleHttpSessionManager() {
        this(new SimpleHttpSessionStore());
    }

    public SimpleHttpSessionManager(HttpSessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public HttpSession createSession() {
        return sessionStore.saveSession(new SimpleHttpSession());
    }

    @Override
    public Optional<HttpSession> getSession(SessionId sessionId) {
        return Optional.ofNullable(sessionStore.getSession(sessionId));
    }

    @Override
    public boolean exist(SessionId sessionId) {
        return sessionStore.contains(sessionId);
    }
}
