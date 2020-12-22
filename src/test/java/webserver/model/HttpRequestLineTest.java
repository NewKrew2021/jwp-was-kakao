package webserver.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HttpRequestLineTest {
    @DisplayName("Valid request lines")
    @ParameterizedTest
    @ValueSource(strings = {
            "POST /", // HTTP/1.0 can be omitted
            "GET /main?k1=val1&k2=val2&k3=val3", // query
            "GET /main?k1[]=val1&k1[]=val2&k1[]=val3&k2=val2", // multiple query
            "DELETE /main?k1[]=val1&k1[]=val2&k1[]=val3&k2=val2 HTTP/1.1", // multiple query and and version
            "PUT /index.htm#anchor1", // hash
            "GET /index.htm?name=kris&ext=lee#anchor-with-mark", // query and hash
            "GET /index.htm?name=kris&ext=lee#anchor HTTP/1.1", // query, hash and version
    })
    void valid(String requestLine) {
        Assertions.assertThatCode(() -> HttpRequestLine.from(requestLine)).doesNotThrowAnyException();
    }

    @DisplayName("Invalid request lines")
    @ParameterizedTest
    @ValueSource(strings = {
            "GET index.htm HTTP/1.0", // path does not started with /
            "GET ?k1=v1 HTTP/1.1", // query without path
            "POST #anchor", // hash without path
            "RUN #anchor", // unknown method
    })
    void invalid(String requestLine) {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> HttpRequestLine.from(requestLine));
    }

    @Test
    void breakDown1() {
        HttpRequestLine requestLine = HttpRequestLine.from("POST /");
        Assertions.assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        Assertions.assertThat(requestLine.getPath()).isEqualTo("/");
        Assertions.assertThat(requestLine.getQuery()).isNull();
        Assertions.assertThat(requestLine.getHash()).isNull();
        Assertions.assertThat(requestLine.getVersion()).isNull();
    }

    @Test
    void breakDown2() {
        HttpRequestLine requestLine = HttpRequestLine.from("DELETE /main?k1[]=val1&k1[]=val2&k1[]=val3&k2=val2 HTTP/1.1");
        Assertions.assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.DELETE);
        Assertions.assertThat(requestLine.getPath()).isEqualTo("/main");
        Assertions.assertThat(requestLine.getQuery()).isEqualTo("k1[]=val1&k1[]=val2&k1[]=val3&k2=val2");
        Assertions.assertThat(requestLine.getHash()).isNull();
        Assertions.assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void breakDown3() {
        HttpRequestLine requestLine = HttpRequestLine.from("GET /index.htm?name=kris&ext=lee#anchor-with-mark");
        Assertions.assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        Assertions.assertThat(requestLine.getPath()).isEqualTo("/index.htm");
        Assertions.assertThat(requestLine.getQuery()).isEqualTo("name=kris&ext=lee");
        Assertions.assertThat(requestLine.getHash()).isEqualTo("anchor-with-mark");
        Assertions.assertThat(requestLine.getVersion()).isNull();
    }
}
