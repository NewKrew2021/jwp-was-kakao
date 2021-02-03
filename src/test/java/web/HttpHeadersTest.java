package web;

import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    @Test
    void create() {
        String data = "Host: localhost:8080" + IOUtils.CRLF +
                "Connection: keep-alive " + IOUtils.CRLF +
                "Upgrade-Insecure-Requests:  1" + IOUtils.CRLF +
                "Keep-Alive:timeout=5, max=1000  " + IOUtils.CRLF;

        HttpHeaders httpHeaders = HttpHeaders.of(Arrays.asList(data.split(IOUtils.CRLF)));
        assertThat(httpHeaders.get("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeaders.get("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.get("Upgrade-Insecure-Requests")).isEqualTo("1");
        assertThat(httpHeaders.get("Keep-Alive")).isEqualTo("timeout=5, max=1000");
    }
}
