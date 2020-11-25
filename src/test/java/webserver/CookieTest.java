package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("쿠키 테스트")
public class CookieTest {
    @DisplayName("로그인 쿠키")
    @Test
    public void login() {
        Cookie cookie = Cookie.login();

        assertThat(cookie.getContent()).isEqualTo("logined=true");
        assertThat(cookie.getPath()).isEqualTo("/");
    }
}