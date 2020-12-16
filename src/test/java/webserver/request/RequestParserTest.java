package webserver.request;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import webserver.Cookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParserTest {
    @Test
    void getRequest() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File("src/test/resources/http_get.txt"));

        HttpRequest request = RequestReader.read(new BufferedReader(new InputStreamReader(in)));

        assertThat(request.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader().getHost()).isEqualTo("localhost:8080");
        assertThat(request.getHeader().getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getHeader().getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getParameter("password")).isEqualTo("password");
        assertThat(request.getParameter("name")).isEqualTo("JaeSung");
    }

    private List<String> getLines(String input) {
        return Arrays.asList(input.split("\n"));
    }

    @Test
    void postRequest() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File("src/test/resources/http_post.txt"));

        HttpRequest request = RequestReader.read(new BufferedReader(new InputStreamReader(in)));

        assertThat(request.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader().getHost()).isEqualTo("localhost:8080");
        assertThat(request.getHeader().getContentLength()).isEqualTo(46);
        assertThat(request.getHeader().getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(request.getHeader().getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getHeader().getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getParameter("id")).isEqualTo("1");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getParameter("password")).isEqualTo("password");
        assertThat(request.getParameter("name")).isEqualTo("JaeSung");
    }

    @Test
    void cookieRequest() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File("src/test/resources/cookie.txt"));

        HttpRequest request = RequestReader.read(new BufferedReader(new InputStreamReader(in)));

        assertThat(request.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getHeader().getHost()).isEqualTo("localhost:8080");
        assertThat(request.getHeader().getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getHeader().getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getHeader().getCookies()).containsExactly(new Cookie("logined", "true", null));
    }
}