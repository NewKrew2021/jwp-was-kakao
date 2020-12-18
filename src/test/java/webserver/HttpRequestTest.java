package webserver;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private InputStream buildInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    @Test
    public void testPostFormUrlEncodedRequest() throws IOException, HttpException {
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        String requestString = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: " + body.length() + "\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                body;

        InputStream is = buildInputStream(requestString);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        HttpRequest req = new HttpRequest(br.readLine(), br);

        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getTarget()).isEqualTo("/user/create");
        assertThat(req.getFirstHeaderValue("Host")).isEqualTo("localhost:8080");
        assertThat(req.getFirstHeaderValueInt("Content-Length")).isEqualTo(body.length());

        assertThat(req.getRequestParam("userId")).isEqualTo("javajigi");
        assertThat(req.getRequestParam("password")).isEqualTo("password");
        assertThat(req.getRequestParam("name")).isEqualTo("박재성");
    }

}
