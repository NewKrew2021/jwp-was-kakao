package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpSessionTest {

    private HttpSession session;

    @BeforeEach
    void setUp() {
        session = new HttpSession("session id");
    }

    @DisplayName("세션 생성시 반드시 세션아이디가 존재한다")
    @Test
    void createNullSessionId() {
        assertThatThrownBy(() -> new HttpSession(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getId() {
        String sessionId = "session id";
        assertThat(new HttpSession(sessionId).getId()).isEqualTo(sessionId);
    }

    @Test
    void setAndGet() {
        Object value = new Object();
        String name = "key1";
        session.setAttribute(name, value);
        assertThat(session.getAttribute(name)).isEqualTo(value);
    }

    @Test
    void removeAttribute() {
        String name = "key1";
        session.setAttribute(name, new Object());
        session.removeAttribute(name);
        assertThat(session.getAttribute(name)).isNull();
    }

    @Test
    void invalidate() {
        session.setAttribute("key1", new Object());
        session.setAttribute("key2", new Object());
        session.invalidate();

        assertAll(
                () -> assertThat(session.getAttribute("key1")).isNull(),
                () -> assertThat(session.getAttribute("key2")).isNull()
        );
    }

}
