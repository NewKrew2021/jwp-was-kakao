package utils;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

class HeaderParserTest {
    private HeaderParser hp;
    String getHeader = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*";
    String postHeader = "POST /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*";

    @BeforeEach
    void setUp() throws IOException {
        String reqHeader = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        InputStream is = new ByteArrayInputStream(reqHeader.getBytes());
        hp = new HeaderParser(is);
    }

    @Test
    void getRequestMethod() {
        RequestMethod method = hp.getRequestMethod();
        assertThat(method).isEqualTo(RequestMethod.GET);
    }
}