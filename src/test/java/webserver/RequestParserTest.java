package webserver;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.HttpMethod.GET;

public class RequestParserTest {

    private BufferedReader bufferedReader;

    @Nested
    @DisplayName("메소드, requestURI, 프로토콜을 파싱한다")
    class RequestLine {
        @BeforeEach
        void setUp() {

            bufferedReader = new BufferedReader(new StringReader(
                    "GET /index.html HTTP/1.1\n" +
                            "Host: localhost:8080\n" +
                            "Connection: keep-alive\n" +
                            "Accept: */*\n\n"));

        }

        @DisplayName("HttpRequest 를 생성한다")
        @Test
        void createHttpRequest() throws IOException {
            assertThat(new RequestParser(bufferedReader).parse()).isNotNull();
        }

        @DisplayName("메소드를 파싱한다")
        @Test
        void parseMethod() throws IOException {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(httpRequest.getMethod()).isEqualTo(GET);
        }

        @DisplayName("RequestURI 를 파싱한다")
        @Test
        void parseRequestURI() throws IOException {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(httpRequest.getRequestURI()).isEqualTo("/index.html");
        }

        @DisplayName("프로토콜을 를 파싱한다")
        @Test
        void parseProtocol() throws IOException {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1");
        }
    }

    @Nested
    @DisplayName("query string 을 파싱한다.")
    class QueryString {
        @BeforeEach
        void setUp() {

            bufferedReader = new BufferedReader(new StringReader(
                    "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                            "Host: localhost:8080\n" +
                            "Connection: keep-alive\n" +
                            "Accept: */*\n\n"));

        }

        @Test
        void parse() throws IOException {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(getUser(httpRequest)).isEqualTo(new User(
                    "javajigi",
                    "password",
                    "박재성",
                    "javajigi@slipp.net"));
        }

        @DisplayName("query stirng 문자열을 Map 으로 변환한다.")
        @Test
        void queryStringLineToMap() {
            RequestParser.UrlEncodedStringParser urlEncodedStringParser = new RequestParser.UrlEncodedStringParser(
                    "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
            assertThat(urlEncodedStringParser.parse())
                    .containsEntry("userId", "javajigi")
                    .containsEntry("password", "password")
                    .containsEntry("name", "박재성")
                    .containsEntry("email", "javajigi@slipp.net");
        }
    }

    @Nested
    @DisplayName("헤더를 파싱한다")
    class Headers {
        @BeforeEach
        void setUp() {

            bufferedReader = new BufferedReader(new StringReader(
                    "POST /user/create HTTP/1.1\n" +
                            "Host: localhost:8080\n" +
                            "Connection: keep-alive\n" +
                            "Content-Length: 93\n" +
                            "Content-Type: application/x-www-form-urlencoded\n" +
                            "Accept: */*\n" +
                            "\n" +
                            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"));

        }

        @Test
        void parse() throws IOException {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(httpRequest.getHeaders())
                    .containsEntry("Host", "localhost:8080")
                    .containsEntry("Connection", "keep-alive")
                    .containsEntry("Content-Length", "93")
                    .containsEntry("Content-Type", "application/x-www-form-urlencoded")
                    .containsEntry("Accept", "*/*");
        }
    }
    @Nested
    @DisplayName("form entity 를 파싱한다")
    class FormEntity {
        @BeforeEach
        void setUp() {

            bufferedReader = new BufferedReader(new StringReader(
                    "POST /user/create HTTP/1.1\n" +
                            "Host: localhost:8080\n" +
                            "Connection: keep-alive\n" +
                            "Content-Length: 93\n" +
                            "Content-Type: application/x-www-form-urlencoded\n" +
                            "Accept: */*\n" +
                            "\n" +
                            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n"));

        }

        @Test
        void parse() throws IOException {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
            assertThat(getUser(httpRequest)).isEqualTo(new User(
                    "javajigi",
                    "password",
                    "박재성",
                    "javajigi@slipp.net"));
        }
    }
    @Nested
    @DisplayName("쿠키를 파싱한다")
    class Cookies {
        @BeforeEach
        void setUp() {
            bufferedReader = new BufferedReader(new StringReader(
                    "GET /user/list HTTP/1.1\n" +
                            "Host: localhost:8080\n" +
                            "Connection: keep-alive\n" +
                            "Accept: */*\n" +
                            "Cookie: logined=true; Idea-32c00508=37ab5797-f595-40c6-b63f-d4e27524f593; Idea-32c008c9=b5b2b305-3b96-4335-9659-dfa0d33877fd\n\n"));
        }

        @Test
        void parse() throws IOException {
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();

            Map<String, String> cookiesMap = httpRequest.getCookies().asMap();
            assertThat(cookiesMap)
                    .containsEntry("logined", "true")
                    .containsEntry("Idea-32c00508", "37ab5797-f595-40c6-b63f-d4e27524f593")
                    .containsEntry("Idea-32c008c9", "b5b2b305-3b96-4335-9659-dfa0d33877fd") ;
        }

        @Test
        void cookieHeaderNotExists() throws IOException {
            bufferedReader = new BufferedReader(new StringReader(
                    "GET /user/list HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*\n\n"));
            HttpRequest httpRequest = new RequestParser(bufferedReader).parse();

            assertThat(httpRequest.getCookies()).isNull();
        }
    }

    public static User getUser(HttpRequest httpRequest) {
        Map<String, String> queryParams = httpRequest.getQueryParams();
        if (queryParams != null) {
            return User.createUser(queryParams);
        }
        Map<String, String> entity = httpRequest.getEntity();
        if (entity != null) {
            return User.createUser(entity);
        }
        throw new IllegalStateException();
    }
}
