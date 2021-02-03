package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeadersTest {
    String header =
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*";

    @DisplayName("헤더 이름으로부터 제대로 된 헤더를 추출하는 지를 테스트")
    @Test
    void testFindByName() {
        HttpRequestHeaders headers = HttpRequestHeaders.of(header);
        assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080");
    }

    @DisplayName("존재하지 않는 헤더 이름이 주어질 때 null을 리턴하는지 테스트")
    @Test
    void checkBadName() {
        HttpRequestHeaders headers = HttpRequestHeaders.of(header);
        assertThat(headers.getHeader("WRONG_HEADER_NAMEh")).isEqualTo(null);
    }
}