package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {

    @DisplayName("Cookie 생성 테스트")
    @Test
    void create() {
        String header = "Cookie: JSESSIONID=DFCD469C0155FA0C17E9BCC6A21A446E; logined=true; grafana_session=fb29c4298834fd1e5d8e40c400266353";

        Cookie cookie = Cookie.of(header);
        assertThat(cookie).isEqualToComparingFieldByField(cookie);
    }

    @DisplayName("Cookie 생성시 isLogined 값을 정상적으로 파싱하는지 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "Cookie: JSESSIONID=DFCD469C; logined=true | true",
            "Cookie: JSESSIONID=DFCD469C; logined=false | false",
            "Cookie: JSESSIONID=DFCD469C; | false"
    }, delimiter = '|')
    void parsingLogined(String cookieHeader, Boolean expectedIsLogined) {
        Cookie cookie = Cookie.of(cookieHeader);

        assertThat(cookie.isLogined()).isEqualTo(expectedIsLogined);
    }
}
