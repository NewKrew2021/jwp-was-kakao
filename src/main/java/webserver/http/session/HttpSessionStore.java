package webserver.http.session;

public interface HttpSessionStore {

    HttpSession getSession(SessionId sessionId);

    HttpSession saveSession(HttpSession session);

    HttpSession removeSession(SessionId sessionId);

    boolean contains(SessionId sessionId);
}
