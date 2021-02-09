package db;

import db.HttpSessions;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.Session;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionsTest {

    @Test
    @DisplayName("세션 일치 테스트")
    void matchTest() {
        User user = new User("dino", "dino", "dino", "dino.bin@kakaocorp.com");
        String id = HttpSessions.getId();
        Session session = new Session();
        session.addAttribute("user", user);
        HttpSessions.addSession(id, session);
        assertThat(HttpSessions.getSession(id).getAttribute("user")).isEqualTo(user);
    }

    @Test
    @DisplayName("세션 불일치 테스트")
    void unMatchTest() {
        User user = new User("dino", "dino", "dino", "dino.bin@kakaocorp.com");
        String id = HttpSessions.getId();
        Session session = new Session();
        session.addAttribute("user", user);
        HttpSessions.addSession(id, session);
        String id2 = HttpSessions.getId();
        assertThat(HttpSessions.getSession(id2)).isNotEqualTo(HttpSessions.getSession(id));
    }
}
