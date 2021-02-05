package web;

import exception.InvalidSessionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.BOOLEAN;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class HttpSessionTest {

    @Test
    void create() {
        HttpSession httpSession = HttpSession.create();

        assertThat(httpSession.getId()).isNotNull();
        assertThat(httpSession).extracting("attributes").asInstanceOf(MAP)
                .isEmpty();
        assertThat(httpSession).extracting("valid").asInstanceOf(BOOLEAN)
                .isTrue();
    }

    @Test
    void attributes() {
        HttpSession httpSession = HttpSession.create();

        assertThat(httpSession.getAttribute("session-id")).isNull();

        httpSession.setAttribute("session-id", "id");
        assertThat(httpSession.getAttribute("session-id")).isEqualTo("id");

        httpSession.removeAttribute("session-id");
        assertThat(httpSession.getAttribute("session-id")).isNull();
    }

    @Test
    void invalidate() {
        HttpSession httpSession = HttpSession.create();

        httpSession.invalidate();

        assertThatThrownBy(httpSession::getId).isInstanceOf(InvalidSessionException.class);
        assertThatThrownBy(httpSession::invalidate).isInstanceOf(InvalidSessionException.class);
        assertThatThrownBy(() -> httpSession.getAttribute("key")).isInstanceOf(InvalidSessionException.class);
        assertThatThrownBy(() -> httpSession.removeAttribute("key")).isInstanceOf(InvalidSessionException.class);
        assertThatThrownBy(() -> httpSession.setAttribute("key", "value")).isInstanceOf(InvalidSessionException.class);
    }
}
