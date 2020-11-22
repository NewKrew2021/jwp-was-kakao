package webserver;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    private BufferedReader bufferedReader;

    @Nested
    @DisplayName("페이지를 로드한다")
    class PageLoad {
        @BeforeEach
        void setUp() {
            //@formatter:off
            bufferedReader = new BufferedReader(new StringReader(
                    "GET /index.html HTTP/1.1\n"
                            + "Host: localhost:8080\n"
                            + "Connection: keep-alive\n"
                            + "Accept: */*"));
            //@formatter:on
        }

        @DisplayName("HttpRequest 를 생성한다")
        @Test
        void createHttpRequest() {
            assertThat(new RequestParser(bufferedReader).parse()).isNotNull();
        }

        @DisplayName("메소드를 파싱한다")
        @Test
        void parseMethod() {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(httpRequest.getMethod()).isEqualTo("GET");
        }

        @DisplayName("RequestURI 를 파싱한다")
        @Test
        void parseRequestURI() {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(httpRequest.getRequestURI()).isEqualTo("/index.html");
        }

        @DisplayName("프로토콜을 를 파싱한다")
        @Test
        void parseProtocol() {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1");
        }
    }

    @Nested
    @DisplayName("query string 을 파싱한다.")
    class QueryString {
        @BeforeEach
        void setUp() {
            //@formatter:off
            bufferedReader = new BufferedReader(new StringReader(
                    "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                            "Host: localhost:8080\n" +
                            "Connection: keep-alive\n" +
                            "Accept: */*\n"));
            //@formatter:on
        }

        @Test
        void parse() {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(httpRequest.getUser()).isEqualTo(new User("javajigi", "password", "박재성", "javagigi@slipp.net"));
        }

        @DisplayName("query stirng 문자열을 Map 으로 변환한다.")
        @Test
        void queryStringLineToMap() {
            QueryStringParser queryStringParser = new QueryStringParser("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
            assertThat(queryStringParser.parse()) //
                    .containsEntry("userId", "javajigi") //
                    .containsEntry("password", "password") //
                    .containsEntry("name", "박재성") //
                    .containsEntry("email", "javajigi@slipp.net");

        }
    }
}
