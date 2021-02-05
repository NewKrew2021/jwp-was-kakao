package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cookies 클래스")
public class CookiesTest {
    String cookieString = "logined=true; count=10";

    @DisplayName("객체 생성 테스트")
    @Test
    public void create() {
        //given
        Cookies cookies = new Cookies(cookieString);

        //when
        String loginValue = cookies.getValueOf("logined");
        String countValue = cookies.getValueOf("count");

        //then
        assertThat(loginValue).isEqualTo("true");
        assertThat(countValue).isEqualTo("10");
    }
}
