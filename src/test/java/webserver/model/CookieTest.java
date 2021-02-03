package webserver.model;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class CookieTest {

    Cookie cookie1 = new Cookie("logined=true");
    Cookie cookie2 = new Cookie("logined=true; aaa=bbb");

    @Test
    void create() {
        assertThat(cookie1.get("logined")).isEqualTo("true");
        assertThat(cookie2.get("aaa")).isEqualTo("bbb");
    }

    @Test
    void makeCookieString() {

        assertThat(cookie1.toString()).isEqualTo("Set-Cookie: logined=true; Path=/\r\n");
        assertThat(cookie2.toString()).isEqualTo("Set-Cookie: aaa=bbb; Path=/\r\n" + "Set-Cookie: logined=true; Path=/\r\n");
    }


}
