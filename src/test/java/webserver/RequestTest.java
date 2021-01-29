package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {
    @Test
    void getMethodTest() {
        Request request = Request.of("GET /index.html HTTP/1.1");
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void getUriTest() {
        Request request = Request.of("GET /index.html HTTP/1.1");
        assertThat(request.getUri()).isEqualTo("/index.html");
    }
}
