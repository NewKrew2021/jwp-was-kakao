package webserver.http.session;

import java.util.Optional;

/**
 * session manager 의 역할
 * <p>
 * 1. 생성된지 일정시간이 지난 오래된 session 을 만료(삭제) 한다
 */
public interface HttpSessionManager {

    HttpSession createSession();

    Optional<HttpSession> getSession(SessionId sessionId);

    boolean exist(SessionId sessionId);

}
