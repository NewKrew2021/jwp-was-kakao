package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SessionUtilsTest {

    private final String SESSION_ID = "ee5c8b2f-d180-4eb4-9a55-8488192b28de";

    @Test
    void extract() {
        String sessionId = SessionUtils.extractSessionId("SessionId=" + SESSION_ID);

        assertThat(sessionId).isEqualTo(SESSION_ID);
    }
}