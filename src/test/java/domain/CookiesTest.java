package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cookie 테스트")
public class CookiesTest {

    @Test
    void constructorTest() {
        String header = "logined=true;dodo=10";
        assertThat(new Cookies(header).toString())
                .contains("dodo=10")
                .contains("logined=true");
    }

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