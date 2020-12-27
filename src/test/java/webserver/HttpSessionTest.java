package webserver;

import org.junit.jupiter.api.Test;
import webserver.request.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    HttpSession session = HttpSession.of("1");

    @Test
    void getId() {
        assertThat(session.getId()).isEqualTo("1");
    }

    @Test
    void setAttribute() {
        session.setAttribute("logined", true);
        assertThat(session.getAttribute("logined")).isEqualTo(true);
    }

    @Test
    void removeAttribute() {
        session.setAttribute("logined", true);
        session.removeAttribute("logined");
        assertThat(session.getAttribute("logined")).isNull();
    }

    @Test
    void invalidate() {
        session.setAttribute("logined", true);
        session.setAttribute("admin", false);
        session.invalidate();
        assertThat(session.getAttribute("logined")).isNull();
        assertThat(session.getAttribute("admin")).isNull();
    }
}