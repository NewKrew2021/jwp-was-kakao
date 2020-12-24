package webserver.http.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Cookie;

import static org.assertj.core.api.Assertions.*;

class CookieParserTest {

    @Test
    @DisplayName("쿠키 파싱 테스트")
    public void parseCookie() {

        Cookie cookie = CookieParser.parse("logined=true");
        assertThat(cookie.getCookie("logined")).isEqualTo("true");

    }

}