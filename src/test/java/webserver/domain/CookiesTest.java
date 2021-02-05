package webserver.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CookiesTest {
    private Cookies cookies;

    @BeforeEach
    void setUp() {
        cookies = new Cookies("session_id=123123; Path=/");
    }

    @Test
    @DisplayName("생성 테스트")
    void createTest() {
        assertThat(cookies.get("session_id").getValue()).isEqualTo("123123");
    }
}