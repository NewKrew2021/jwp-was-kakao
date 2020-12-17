package webserver;

import org.junit.jupiter.api.Test;

import static context.ApplicationContext.sessionRegistry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SessionRegistryTest {
    @Test
    void hasSession() {
        assertFalse(sessionRegistry.hasSession("sessionKey"));
    }

    @Test
    void createSession() {
        String sessionKey = sessionRegistry.createSession();
        System.out.println(sessionKey);
        assertThat(sessionKey).isNotNull();
    }

    @Test
    void getSession() {
        String sessionKey = sessionRegistry.createSession();
        assertThat(sessionRegistry.getSession(sessionKey)).isNotNull();
    }

    @Test
    void noSession() {
        assertThat(sessionRegistry.getSession("sessionKey")).isNull();
    }
}