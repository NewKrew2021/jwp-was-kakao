package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * v String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
 * v void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
 * v Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
 * * void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
 * * void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
 */
public class HttpSessionTest {
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
        HttpSession session = new HttpSession("session id");

        Object value = new Object();
        String name = "key1";
        session.setAttribute(name, value);
        assertThat(session.getAttribute(name)).isEqualTo(value);
    }

    private static class HttpSession {

        private final String id;
        private final Map<String, Object> map = new HashMap<>();

        public HttpSession(String id) {
            this.id = id;
            if (id == null) {
                throw new IllegalArgumentException();
            }
        }

        public String getId() {
            return id;
        }

        public void setAttribute(String name, Object value) {
            map.put(name, value);
        }

        public Object getAttribute(String name) {
            return map.get(name);
        }
    }
}
