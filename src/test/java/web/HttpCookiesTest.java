package web;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;


class HttpCookiesTest {

    @Test
    void createFromString() {
        HttpCookies cookie = HttpCookies.from("logined=true; Path=/");

        assertThat(cookie).extracting("attributes").asInstanceOf(MAP)
                .contains(entry("logined", "true"))
                .contains(entry("Path", "/"));
    }

    @Test
    void createLoginedCookie() {
        HttpCookies logined = HttpCookies.logined();

        assertThat(logined.toString()).contains("logined=true", "Path=/");
    }

    @Test
    void createNotLoginedCookie() {
        HttpCookies notLogined = HttpCookies.notLogined();

        assertThat(notLogined.toString()).contains("logined=false", "Path=/");
    }

    @Test
    void isLogined() {
        HttpCookies logined = HttpCookies.logined();
        HttpCookies notLogined = HttpCookies.notLogined();

        assertThat(logined.isLogined()).isTrue();
        assertThat(notLogined.isLogined()).isFalse();
    }
}
