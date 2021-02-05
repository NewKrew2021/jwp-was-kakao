package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
