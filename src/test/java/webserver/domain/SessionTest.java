package webserver.domain;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {

    @Test
    @DisplayName("세션 일치 테스트")
    void matchTest() {
        User user = new User("dino", "dino", "dino", "dino.bin@kakaocorp.com");
        Session session = new Session();
        session.addAttribute("user", user);
        assertThat(session.getAttribute("user")).isEqualTo(user);
    }
}
