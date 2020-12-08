package webserver.http.session;

import java.util.Optional;

public class SimpleHttpSessionManager implements HttpSessionManager{

    private HttpSessionStore httpSessionStore;

    public SimpleHttpSessionManager() {
        this(new SimpleHttpSessionStore());
    }

    public SimpleHttpSessionManager(HttpSessionStore httpSessionStore) {
        this.httpSessionStore = httpSessionStore;
    }

    @Override
    public HttpSession createSession() {
        return httpSessionStore.saveSession(new SimpleHttpSession());
    }

    @Override
    public Optional<HttpSession> getSession(SessionId sessionId) {
        return Optional.ofNullable(httpSessionStore.getSession(sessionId));
    }

    @Override
    public boolean exist(SessionId sessionId) {
        return httpSessionStore.contains(sessionId);
    }
}
