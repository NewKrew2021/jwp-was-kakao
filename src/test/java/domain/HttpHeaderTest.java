package domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HttpHeaderTest {

    static List<String> rawHttpHeader = new ArrayList<>();
    HttpHeader httpHeader = new HttpHeader(rawHttpHeader);

    @BeforeAll
    static void setUp() {
        rawHttpHeader.add("Host: localhost:8080");
        rawHttpHeader.add("Connection: keep-alive");
        rawHttpHeader.add("Cookie: logined=true; username=javajigi");
        rawHttpHeader.add("Accept: */*");
    }

    @DisplayName("get Header")
    @ParameterizedTest
    @CsvSource({"Host,localhost:8080", "Connection,keep-alive"})
    void getHeader(String key, String value) {
        assertThat(httpHeader.getHeader(key)).isEqualTo(value);
    }

    @DisplayName("get Cookie")
    @ParameterizedTest
    @CsvSource({"logined,true", "username,javajigi"})
    void getCookie(String key, String value) {
        assertThat(httpHeader.getCookie(key)).isEqualTo(value);
    }
    
}
