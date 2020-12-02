package webserver.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestTest {

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
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        Assertions.assertThatCode(() -> new Request(in)).doesNotThrowAnyException();
    }

    @DisplayName("Invalid request lines")
    @ParameterizedTest
    @ValueSource(strings = {
            "GET index.htm HTTP/1.0", // path does not started with /
            "GET ?k1=v1 HTTP/1.1", // query without path
            "POST #anchor", // hash without path
    })
    void invalid(String requestLine) {
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Request(in));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3 | GET /main?k1=val1&k2=val2&k3=val3",
            "2 | GET /main?k1=val1&k2=val2&k2=val3",
    })
    void parameters(int expected, String requestLine) throws IOException {
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        Assertions.assertThat(new Request(in).getParameters().size()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "k1 | val1 | GET /main?k1=val1&k2=val2&k3=val3",
            "k2 | val2 | GET /main?k1=val1&k2=val2&k2=val3",
    })
    void parameter(String queryKey, String expected, String requestLine) throws IOException {
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        Assertions.assertThat(new Request(in).getParameter(queryKey)).isEqualTo(expected);
    }
}
