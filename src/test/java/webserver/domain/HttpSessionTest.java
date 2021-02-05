package webserver.domain;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    @Test
    @DisplayName("세션 일치 테스트")
    void matchTest() {
        User user = new User("dino", "dino", "dino", "dino.bin@kakaocorp.com");
        String id = HttpSession.getId();
        HttpSession.setAttribute(id, user);
        assertThat(HttpSession.getAttribute(id)).isEqualTo(user);
    }

    @Test
    @DisplayName("세션 불일치 테스트")
    void unMatchTest() {
        User user = new User("dino", "dino", "dino", "dino.bin@kakaocorp.com");
        String id = HttpSession.getId();
        HttpSession.setAttribute(id, user);
        String id2 = HttpSession.getId();
        assertThat(HttpSession.getAttribute(id2)).isNotEqualTo(user);
    }
}
