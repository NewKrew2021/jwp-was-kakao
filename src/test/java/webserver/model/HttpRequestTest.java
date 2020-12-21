package webserver.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpRequestTest {
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3 | GET /main?k1=val1&k2=val2&k3=val3",
            "2 | GET /main?k1=val1&k2=val2&k2=val3",
    })
    void parameters(int expected, String requestLine) throws IOException {
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        Assertions.assertThat(HttpRequest.from(in).getParameters().size()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "k1 | val1 | GET /main?k1=val1&k2=val2&k3=val3",
            "k2 | val2 | GET /main?k1=val1&k2=val2&k2=val3",
    })
    void parameter(String queryKey, String expected, String requestLine) throws IOException {
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        Assertions.assertThat(HttpRequest.from(in).getParameter(queryKey)).isEqualTo(expected);
    }

    @Test
    void createUser() throws IOException {
        String requestLine =
                "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        Assertions.assertThat(HttpRequest.from(in).getParameters())
                .containsEntry("password", "password")
                .containsEntry("name", "박재성")
                .containsEntry("userId", "javajigi")
                .containsEntry("email", "javajigi@slipp.net");
    }

    @Test
    void createUserWithPost() throws IOException {
        String[] requestLines = {
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 93",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        };
        InputStream in = new ByteArrayInputStream(String.join("\r\n", requestLines).getBytes());
        Assertions.assertThat(HttpRequest.from(in).getParameters())
                .containsEntry("password", "password")
                .containsEntry("name", "박재성")
                .containsEntry("userId", "javajigi")
                .containsEntry("email", "javajigi@slipp.net");
    }
}
