package domain;

import exception.InvaliCookieException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Cookie 테스트")
public class CookiesTest {

    @DisplayName("cookies 생성자 테스트")
    @Test
    void constructorTest() {
        String header = "logined=true;dodo=10";
        assertThat(new Cookies(header).toString())
                .contains("dodo=10")
                .contains("logined=true");
    }

    @DisplayName("잘못된 cookies(= 두개) 생성자 테스트")
    @Test
    void invalidConstructorTest() {
        String header = "logi=ned=true;dodo=10";
        assertThatThrownBy(()->{
            new Cookies(header);
        }).isInstanceOf(InvaliCookieException.class);
    }

    @DisplayName("잘못된 cookies(= 없음) 생성자 테스트")
    @Test
    void invalidConstructorByNoEqualTest() {
        String header = "logined;dodo=10";
        assertThatThrownBy(()->{
            new Cookies(header);
        }).isInstanceOf(InvaliCookieException.class);
    }

    @DisplayName("coookie값 가져오기 테스트")
    @Test
    void valueOfTest() {
        String header = "logined=true;dodo=10";
        Cookies cookies = new Cookies(header);
        assertThat(cookies.getValueOf("logined"))
                .isEqualTo("true");
        assertThat(cookies.getValueOf("dodo"))
                .isEqualTo("10");
    }
}
