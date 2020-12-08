package webserver.http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

abstract class HttpSessionStoreTest {

    HttpSessionStore httpSessionStore;

    @DisplayName("sessionId 로 session 을 조회 할 수 있다")
    @Test
    void getSession(){
        HttpSession httpSession = new SimpleHttpSession();
        httpSessionStore.saveSession(httpSession);

        HttpSession found = httpSessionStore.getSession(httpSession.getId());

        assertThat(found).isEqualTo(httpSession);
    }

    @DisplayName("session 이 존재하지 않으면 null 을 돌려준다")
    @Test
    void notExistSession(){
        SessionId notExistSessionId = createSessionId();
        HttpSession found = httpSessionStore.getSession(notExistSessionId);

        assertThat(found).isNull();
    }

    private SessionId createSessionId() {
        return () -> "1234567890";
    }

    @DisplayName("session 을 저장하면 저장된 session 을 돌려준다")
    @Test
    void saveSession(){
        HttpSession httpSession = new SimpleHttpSession();
        assertThat(httpSessionStore.saveSession(httpSession)).isEqualTo(httpSession);
    }

    @DisplayName("null 을 저장하면 exception 을 던진다")
    @Test
    void shouldNotNullWhenSaveSessionOrThrowException(){
        assertThatThrownBy(() -> httpSessionStore.saveSession(null))
                .isInstanceOf(HttpSessionStoreException.class);

    }

    @DisplayName("sessionId 로 저장된 session 을 삭제 할 수 있다")
    @Test
    void removeSession(){
        HttpSession httpSession = new SimpleHttpSession();
        httpSessionStore.saveSession(httpSession);

        assertThat(httpSessionStore.removeSession(httpSession.getId())).isEqualTo(httpSession);
        assertThat(httpSessionStore.getSession(httpSession.getId())).isNull();

    }

}

class SimpleSessionStoreTest extends HttpSessionStoreTest {

    @BeforeEach
    void setUp(){
        httpSessionStore = new SimpleHttpSessionStore();
    }
}