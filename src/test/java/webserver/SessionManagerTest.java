package webserver;

import org.junit.jupiter.api.Test;

import static context.ApplicationContext.sessionManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SessionManagerTest {
    @Test
    void hasSession() {
        assertFalse(sessionManager.hasSession("sessionKey"));
    }

    @Test
    void createSession() {
        String sessionKey = sessionManager.createSession();
        System.out.println(sessionKey);
        assertThat(sessionKey).isNotNull();
    }

    @Test
    void getSession() {
        String sessionKey = sessionManager.createSession();
        assertThat(sessionManager.getSession(sessionKey)).isNotNull();
    }

    @Test
    void noSession() {
        assertThat(sessionManager.getSession("sessionKey")).isNull();
    }
}