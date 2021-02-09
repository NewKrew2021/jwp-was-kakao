package webserver.model;

import org.assertj.core.api.Assertions;
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

    @DisplayName("특정 헤더 가져오기")
    @Test
    void getHeader() {
        Assertions.assertThat(httpHeader1.getHeader("Host")).isEqualTo("localhost:8080");
        Assertions.assertThat(httpHeader1.getHeader("Connection")).isEqualTo("keep-alive");
        Assertions.assertThat(httpHeader1.getHeader("Accept")).isEqualTo("*/*");
    }

    @DisplayName("특정 쿠기 가져오기")
    @Test
    void getCookie() {
        Assertions.assertThat(httpHeader1.getCookie("logined")).isEqualTo(null);
        Assertions.assertThat(httpHeader1.getCookie("username")).isEqualTo(null);
        Assertions.assertThat(httpHeader2.getCookie("logined")).isEqualTo("true");
        Assertions.assertThat(httpHeader2.getCookie("username")).isEqualTo("javajigi");
    }

    @DisplayName("헤더스트링")
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
        Assertions.assertThat(httpHeader1.toString()).isEqualTo(headerString1);
        Assertions.assertThat(httpHeader2.toString()).isEqualTo(headerString2);
    }
    
}
