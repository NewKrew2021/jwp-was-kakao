package webserver.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookiesTest {

    @Test
    void cookieTest() {
        Cookies cookies = new Cookies("logined=true; Session=324t1qegefb;\r\n");
        cookies.getCookie("Session");
        Cookie cookie = new Cookie("Session=324t1qegefb");
        assertThat(cookies.getCookie("Session")).isEqualTo(cookie);
    }
}
