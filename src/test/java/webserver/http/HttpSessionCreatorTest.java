package webserver.http;


import org.junit.jupiter.api.Test;

/**
 * * 세션 아이디를 생성한다
 * * 새션객체를 세션아이디와 함께 생성한다
 */
public class HttpSessionCreatorTest {
    @Test
    void createSession() {
        HttpSessionCreator httpSessionCreator = new HttpSessionCreator();
        assertThat(httpSessionCreator.create()).isInstanceOf(HttpSession.class);
    }
}
