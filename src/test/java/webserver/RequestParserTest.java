package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpRequest;

import java.io.BufferedReader;
import java.io.StringReader;

public class RequestParserTest {
    @Test
    void instantiation() {
        //@formatter:off
        BufferedReader bufferedReader = new BufferedReader(new StringReader(
                "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*"));
        //@formatter:on
        new RequestParser(bufferedReader);
    }
}
