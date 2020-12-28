package webserver.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpSessionTest {
    HttpSession oldSession;

    @BeforeEach
    void setupSession() {
        oldSession = new HttpSession();
        oldSession.setAttribute("key1", "val1");
        oldSession.setAttribute("key2", "val2");
        oldSession.setAttribute("self", oldSession);
    }

    @Test
    void newSession() {
        Assertions.assertThatCode(() -> {
            HttpSession session = new HttpSession();
        }).doesNotThrowAnyException();
    }

    @Test
    void resumeSession() {
        HttpSession newSession = HttpSession.of(oldSession.getId());
        Assertions.assertThat(newSession.getId()).isEqualTo(oldSession.getId());
    }

    @Test
    void setGetAttribute() {
        HttpSession newSession = HttpSession.of(oldSession.getId());
        oldSession.setAttribute("key3", "val3");
        Assertions.assertThat(newSession.getAttribute("key1")).isEqualTo("val1");
        Assertions.assertThat(newSession.getAttribute("key2")).isEqualTo("val2");
        Assertions.assertThat(newSession.getAttribute("self")).isEqualTo(newSession);
        Assertions.assertThat(newSession.getAttribute("key3")).isEqualTo("val3");
    }

    @Test
    void removeAttribute() {
        HttpSession newSession = HttpSession.of(oldSession.getId());
        oldSession.removeAttribute("key2");
        Assertions.assertThat(newSession.getAttribute("key1")).isEqualTo("val1");
        Assertions.assertThat(newSession.getAttribute("key2")).isNull();
        Assertions.assertThat(newSession.getAttribute("self")).isEqualTo(newSession);
    }

    @Test
    void invalidate() {
        oldSession.invalidate();
        HttpSession newSession = HttpSession.of(oldSession.getId());
        Assertions.assertThat(newSession).isNull();
    }
}
