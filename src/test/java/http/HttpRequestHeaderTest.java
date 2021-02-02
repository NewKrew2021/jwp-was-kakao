package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HttpRequestHeaderTest {

    @DisplayName("HttpRequestHeader 생성 테스트")
    @Test
    void create() {
        String headerLine = "HeaderName: HeaderContent";
        HttpRequestHeader header = HttpRequestHeader.of(headerLine);

        assertThat(header.getHeaderName()).isEqualTo("HeaderName");
        assertThat(header.getHeaderContent()).isEqualTo("HeaderContent");
    }
}