package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeaderTest {

    private final String testDirectory = "src/test/resources-test/";

    @Test
    void request_header_PARSE_TEST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTTP"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest request = new HttpRequest(bufferedReader);
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getProtocol()).isEqualTo("HTTP/1.1");
        assertThat(request.getHost()).isEqualTo("localhost:8080");
        assertThat(request.getConnection()).isEqualTo("keep-alive");
        assertThat(request.getAccept()).isEqualTo("*/*");

    }

    @Test
    void request_header_PRINT_TEST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTTP"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest request = new HttpRequest(bufferedReader);
        request.getHeader().print();
    }
}
