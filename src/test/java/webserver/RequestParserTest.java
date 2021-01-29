package webserver;

import dto.RequestHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class RequestParserTest {

    private RequestParser requestParser;

    String requestHeader1 = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";

    String requestHeader2 = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";


    @BeforeEach
    public void setUp() {

    }

    @Test
    void method() {
        RequestHeader requestHeader = requestParser.parseHeader(requestHeader1);
        assertThat(requestHeader.getMethod()).isEqualTo("GET");
    }

    @Test
    void path() {
        RequestHeader requestHeader = requestParser.parseHeader(requestHeader1);
        assertThat(requestHeader.getPath()).isEqualTo("/index.html");
    }

    @Test
    void queryParameter() {
        RequestHeader requestHeader = requestParser.parseHeader(requestHeader2);

        List<String> parameters = new ArrayList<>();
        parameters.add("javajigi");
        parameters.add("password");
        parameters.add("%EB%B0%95%EC%9E%AC%EC%84%B1");
        parameters.add("javajigi%40slipp.net");

        assertThat(requestHeader.getParameters()).isEqualTo(parameters);
    }
}
