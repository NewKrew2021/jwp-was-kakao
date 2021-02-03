package web;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HttpBodyTest {
    @Test
    void create() {
        HttpHeaders httpHeaders = HttpHeaders.of(Arrays.asList("Content-Length: 93"));
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(
                        "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net".getBytes(StandardCharsets.UTF_8))));
        HttpBody httpBody = HttpBody.of(httpHeaders, br);

        assertThat(httpBody.getBody()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net".getBytes());
    }

    @Test
    void createWithContentType() {
        HttpHeaders httpHeaders = HttpHeaders.of(Arrays.asList("Content-Length: 93", "Content-Type: application/x-www-form-urlencoded"));
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(
                        "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net".getBytes(StandardCharsets.UTF_8))));
        HttpBody httpBody = HttpBody.of(httpHeaders, br);

        assertThat(httpBody.getBody()).isEqualTo("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net".getBytes());
    }
}
