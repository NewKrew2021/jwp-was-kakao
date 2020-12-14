package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private final String testDirectory = "src/test/resources-test/";

    @Test
    void isLoginTest() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTML_LOGIN_TRUE"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(bufferedReader);
        assertThat(httpRequest.isLogin()).isTrue();
    }
    @Test
    void isLoginFalseTest() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "GET_HTML_LOGIN_FALSE"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(bufferedReader);
        assertThat(httpRequest.isLogin()).isFalse();
    }

}
