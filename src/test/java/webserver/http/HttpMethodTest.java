package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HttpMethodTest {

    @Test
    @DisplayName("HTTP 메서드 GET 확인")
    public void test_GET_METHOD() {

        HttpMethod method = HttpMethod.of("get");
        assertThat(method).isEqualTo(HttpMethod.GET);

    }

    @Test
    @DisplayName("HTTP 메서드 GET 확인")
    public void test_POST_METHOD() {

        HttpMethod method = HttpMethod.of("GET");
        assertThat(method).isEqualTo(HttpMethod.GET);

    }

}
