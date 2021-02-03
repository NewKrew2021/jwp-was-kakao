package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderTest {

    @DisplayName("헤더 파싱이 제대로 이뤄지는지 테스트")
    @Test
    void checkInitialize() {
        HttpRequestHeader header= HttpRequestHeader.of("Host: localhost:8080");

        assertThat(header.getHeaderName()).isEqualTo("Host");
        assertThat(header.getHeaderContent()).isEqualTo("localhost:8080");
    }
}