package web;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;


class HttpCookiesTest {

    @Test
    void createFromString() {
        HttpCookies cookie = HttpCookies.from("logined=true; Path=/");

        assertThat(cookie.getAttributes())
                .contains(entry("logined", "true"))
                .contains(entry("Path", "/"));
    }
}
