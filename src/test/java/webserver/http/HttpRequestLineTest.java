package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineTest {

    @Test
    @DisplayName("메서드 조회 확인")
    void getMethod() {

        HttpRequestLine line = new HttpRequestLine(HttpMethod.GET, "/", null, new Protocol("HTTP", "1.1"));
        assertThat(line.getMethod()).isEqualTo(HttpMethod.GET);

    }

    @Test
    @DisplayName("Path 조회 확인")
    void getPath() {

        HttpRequestLine line = new HttpRequestLine(HttpMethod.GET, "/", null, new Protocol("HTTP", "1.1"));
        assertThat(line.getPath()).isEqualTo("/");

    }

    @Test
    @DisplayName("쿼리 스트링 조회 확인")
    void getQueryString() {

        HttpRequestLine line = new HttpRequestLine(HttpMethod.GET, "/", null, new Protocol("HTTP", "1.1"));
        assertThat(line.getQueryString()).isEqualTo(null);

    }

    @Test
    @DisplayName("Protocol 조회 확인")
    void getProtocol() {

        HttpRequestLine line = new HttpRequestLine(HttpMethod.GET, "/", null, new Protocol("HTTP", "1.1"));
        assertThat(line.getProtocol()).isEqualTo("HTTP");

    }

    @Test
    @DisplayName("버전 조회 확인")
    void getVersion() {

        HttpRequestLine line = new HttpRequestLine(HttpMethod.GET, "/", null, new Protocol("HTTP", "1.1"));
        assertThat(line.getVersion()).isEqualTo("1.1");

    }
}