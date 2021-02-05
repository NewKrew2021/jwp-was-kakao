package webserver.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpSessionTest {
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession();
        httpSession.setAttribute("logined", "true");
    }

    @Test
    @DisplayName("생성 테스트")
    void createTest() {
        assertThat(httpSession.getAttribute("logined")).isEqualTo("true");
    }

    @Test
    @DisplayName("invalidate 테스트")
    void invalidateTest() {
        httpSession.invalidate();

        assertThat(httpSession.contains("logined")).isFalse();
    }

    @Test
    @DisplayName("제거 테스트")
    void removeTest() {
        httpSession.removeAttribute("logined");

        assertThat(httpSession.contains("logined")).isFalse();
    }
}