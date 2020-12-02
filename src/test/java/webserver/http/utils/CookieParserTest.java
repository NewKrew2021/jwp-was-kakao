package webserver.http.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import webserver.http.Cookie;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CookieParserTest {

    @DisplayName("key=value 쌍은 ; 로 구분한다")
    @Test
    void delimiter(){
        CookieParser parser = new CookieParser();

        List<Cookie> cookies = parser.parse("name=nio; gender=man");

        assertThat(cookies).hasSize(2);
    }

    @DisplayName("null 또는 공백 문자열을 넘겨주면 빈 배열을 돌려준다")
    @ParameterizedTest
    @NullAndEmptySource
    void nullAndEmpty(String cookieValue){
        CookieParser parser = new CookieParser();

        List<Cookie> cookies = parser.parse(cookieValue);

        assertThat(cookies).hasSize(0);
    }

    @DisplayName("key=value 문자열로 Cookie 를 생성할 수 있다")
    @Test
    void getValue(){
        CookieParser parser = new CookieParser();

        List<Cookie> cookies = parser.parse("name=nio");

        assertThat(cookies.get(0).getName()).isEqualTo("name");
        assertThat(cookies.get(0).getValue()).isEqualTo("nio");
    }

}