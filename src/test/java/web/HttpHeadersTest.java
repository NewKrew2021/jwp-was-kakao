package web;

import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    @Test
    void create() {
        String data = "Host: localhost:8080" + IOUtils.NEW_LINE +
                "Connection: keep-alive" + IOUtils.NEW_LINE +
                "Upgrade-Insecure-Requests: 1" + IOUtils.NEW_LINE;

        HttpHeaders httpHeaders = HttpHeaders.of(Arrays.asList(data.split(IOUtils.NEW_LINE)));
        assertThat(httpHeaders.get("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeaders.get("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.get("Upgrade-Insecure-Requests")).isEqualTo("1");
    }
}