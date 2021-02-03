package web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import utils.IOUtils;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    @Test
    void create() {
        HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK);
        httpResponse.addHeader("Location", "/user/1");

        assertThat(httpResponse.toString()).isEqualTo(
                "HTTP/1.1 200 OK" + IOUtils.CRLF
                        + "Location: /user/1" + IOUtils.CRLF);

        httpResponse.setBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        assertThat(httpResponse.toString()).isEqualTo(
                "HTTP/1.1 200 OK" + IOUtils.CRLF
                        + "Location: /user/1" + IOUtils.CRLF + IOUtils.CRLF
                        + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net" + IOUtils.CRLF
        );
    }
}
