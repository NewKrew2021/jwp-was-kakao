package http;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    String request = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=wonsik&password=1234&name=wonsik&email=wonsik@kakaocorp.com";
    HttpRequestParser parser = new HttpRequestParser();


    @Test
    void testMethod() {
        parser.parse(request);
        HttpRequest httpRequest =
                new HttpRequest(parser.getRequestMethod(), parser.getUri(), parser.getRequestHeaders(), parser.getBody());

        assertThat(httpRequest.getRequestMethod()).isEqualTo(RequestMethod.POST);
        assertThat(httpRequest.getUri()).isEqualTo("/user/create");
    }

    @Test
    void testHeader() {
        parser.parse(request);
        HttpRequest httpRequest =
                new HttpRequest(parser.getRequestMethod(), parser.getUri(), parser.getRequestHeaders(), parser.getBody());
        HttpRequestHeaders headers = httpRequest.getRequestHeaders();

        assertThat(headers.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(headers.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(headers.getHeader("Content-Length")).isEqualTo("59");
        assertThat(headers.getHeader("Wrong-Name")).isEqualTo(null);
    }
}