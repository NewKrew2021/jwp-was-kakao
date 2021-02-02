package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HttpRequestHeadersTest {

    @DisplayName("HttpRequestHeaders 생성 테스트")
    @Test
    void create() {
        String headerLines = "Header1: Content1\nHeader2: Content2";
        HttpRequestHeaders headers = HttpRequestHeaders.of(headerLines);

        assertThat(headers.getHeader("Header1")).isEqualTo("Content1");
        assertThat(headers.getHeader("Header2")).isEqualTo("Content2");
    }
}