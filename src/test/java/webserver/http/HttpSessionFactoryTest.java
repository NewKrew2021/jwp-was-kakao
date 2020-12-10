package webserver.http;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * v 세션객체를 생성한다
 * * 세션아이디가 존재하면 동일한 객체를 리턴한다
 * * 주어진 세션아이디를 가진 세션객체를 생성한다.
 * * 세션아이디가 존재하지 않거나 널이면 새로운 객체를 리턴한다
 */
public class HttpSessionFactoryTest {
    @Test
    void createSession() {
        HttpSessionFactory httpSessionFactory = new HttpSessionFactory();
        assertThat(httpSessionFactory.create(null)).isInstanceOf(HttpSession.class);
    }

    @DisplayName("세션 아이디가 널이면 새로운 세션을 생성한다")
    @Test
    void createSessionWithNullId() {
        HttpSessionFactory httpSessionFactory = new HttpSessionFactory();
        assertThat(httpSessionFactory.create(null)).isNotEqualTo(httpSessionFactory.create(null));
    }

    private static class HttpSessionFactory {
        public HttpSession create(String sessionId) {
            return new HttpSession("");
        }
    }
}
