package webserver.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeaderTest {

    List<String> headerStrings = new ArrayList<>();
    HttpHeader httpHeader1;
    HttpHeader httpHeader2;

    @BeforeEach
    public void setUp() {
        headerStrings.add("Host: localhost:8080");
        headerStrings.add("Connection: keep-alive");
        headerStrings.add("Accept: */*");

        httpHeader1 = new HttpHeader(headerStrings);
        headerStrings.add("Cookie: logined=true; username=javajigi");
        httpHeader2 = new HttpHeader(headerStrings);
    }

    @DisplayName("get Header")
    @Test
    void getHeader() {
        assertThat(httpHeader1.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeader1.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeader1.getHeader("Accept")).isEqualTo("*/*");
    }

    @DisplayName("get Cookie")
    @Test
    void getCookie() {
        assertThat(httpHeader1.getCookie("logined")).isEqualTo(null);
        assertThat(httpHeader1.getCookie("username")).isEqualTo(null);
        assertThat(httpHeader2.getCookie("logined")).isEqualTo("true");
        assertThat(httpHeader2.getCookie("username")).isEqualTo("javajigi");
    }

    @DisplayName("headerString")
    @Test
    void headerString() {
        String headerString1 = "Accept: */*\r\n"
                + "Connection: keep-alive\r\n"
                + "Host: localhost:8080\r\n"
                + "\r\n";

        String headerString2 = "Accept: */*\r\n"
                + "Connection: keep-alive\r\n"
                + "Host: localhost:8080\r\n"
                + "Set-Cookie: logined=true; Path=/\r\n"
                + "Set-Cookie: username=javajigi; Path=/\r\n"
                + "\r\n";
        assertThat(httpHeader1.toString()).isEqualTo(headerString1);
        assertThat(httpHeader2.toString()).isEqualTo(headerString2);
    }
    
}
