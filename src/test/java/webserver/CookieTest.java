package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @Test
    public void login() {
        Cookie cookie = Cookie.from("logined=true");

        assertThat(cookie.getContent()).isEqualTo("logined=true");
        assertThat(cookie.getName()).isEqualTo("logined");
        assertThat(cookie.getValue()).isEqualTo("true");
    }
}