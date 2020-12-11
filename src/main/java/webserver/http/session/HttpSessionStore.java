package webserver.http.session;

/**
 * session 저장 / 조회 역할을 합니다
 */
public interface HttpSessionStore {

    HttpSession getSession(SessionId sessionId);

    HttpSession saveSession(HttpSession session);

    HttpSession removeSession(SessionId sessionId);

    boolean contains(SessionId sessionId);
}
