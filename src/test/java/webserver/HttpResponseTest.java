package webserver;

import org.junit.jupiter.api.Test;
import webserver.constant.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    public void testBasicResponse() {
        String resp = new HttpResponse(HttpStatus.OK)
                .addHeader("x-hello", "world!")
                .setBody("hello response!".getBytes(), true)
                .toString();

        String[] lines = resp.split("\r\n");
        assertThat(lines[0]).isEqualTo("HTTP/1.1 200 OK");
        assertThat(lines[lines.length - 1]).isEqualTo("hello response!");

        assertThat(resp).contains("\r\nx-hello: world!\r\n");
        assertThat(resp).contains("\r\ncontent-length: 15\r\n");
    }

}
