package webserver.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookieTest {
    private Cookie cookie;

    @BeforeEach
    void setUp() {
        cookie = new Cookie("session_id", "123123");
    }

    @Test
    @DisplayName("생성 테스트")
    void createTest() {
        assertEquals(cookie.getName(), "session_id");
    }

    @Test
    @DisplayName("매치 테스트")
    void matchTest() {
        assertTrue(cookie.match("session_id"));
    }
}