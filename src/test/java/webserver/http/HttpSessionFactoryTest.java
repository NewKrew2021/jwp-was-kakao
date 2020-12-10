package webserver.http;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * v 세션객체를 생성한다
 * * 세션아이디가 존재하면 동일한 객체를 리턴한다
 * * 세션아이디가 존재하지 않거나 널이면 새로운 객체를 리턴한다
 */
public class HttpSessionFactoryTest {
    @Test
    void createSession() {
        HttpSessionFactory httpSessionFactory = new HttpSessionFactory();
        assertThat(httpSessionFactory.create(null)).isInstanceOf(HttpSession.class);
    }

    private static class HttpSessionFactory {
        public HttpSession create(String sessionId) {
            return new HttpSession("");
        }
    }
}
