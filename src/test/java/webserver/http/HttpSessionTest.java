package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {
    HttpSession session;

    @BeforeEach
    public void setUp() {
        session = new HttpSession();
    }

    @Test
    public void attribute() {
        session.setAttribute("test", "value");
        session.setAttribute("test2", "value2");
        assertThat(session.getAttribute("test")).isEqualTo("value");
        assertThat(session.getAttribute("test2")).isEqualTo("value2");
        assertThat(session.getAttribute("test3")).isNull();

        session.removeAttribute("test2");
        assertThat(session.getAttribute("test")).isEqualTo("value");
        assertThat(session.getAttribute("test2")).isNull();

        session.invalidate();
        assertThat(session.getAttribute("test")).isNull();
    }
}
