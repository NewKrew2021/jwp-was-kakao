package webserver.http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

abstract class HttpSessionManagerTest {

    HttpSessionManager sessionManager;

    @DisplayName("session 을 생성 할 수 있다")
    @Test
    void createSession(){
        assertThat(sessionManager.createSession()).isNotNull();
    }

    @DisplayName("sessionId 로 session 을 조회 할 수 있다")
    @Test
    void getSession(){
        HttpSession httpSession = sessionManager.createSession();

        assertThat(sessionManager.getSession(httpSession.getId()).get()).isEqualTo(httpSession);
    }

    @DisplayName("sessionId 로 session 존재 여부를 확인 할 수 있다")
    @Test
    void exist(){
        HttpSession httpSession = sessionManager.createSession();
        HttpSession notExist = new SimpleHttpSession();

        assertThat(sessionManager.exist(httpSession.getId())).isTrue();
        assertThat(sessionManager.exist(notExist.getId())).isFalse();
    }

}

class SimpleHttpSessionManagerTest extends HttpSessionManagerTest {

    @BeforeEach
    void setUp(){
        sessionManager = new SimpleHttpSessionManager();
    }

}