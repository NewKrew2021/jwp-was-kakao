package utils;

import controller.Handler;
import controller.StaticController;
import controller.TemplateController;
import controller.UserController;
import http.HttpRequest;
import http.HttpRequestParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherTest {

    @DisplayName("templateHandler를 반환하는지 테스트")
    @ParameterizedTest
    @CsvSource({"/index.html", "/favicon.ico"})
    void templateRequest(String uri) {
        HttpRequest request = new HttpRequestParser("GET " + uri + " HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").parse();

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(TemplateController.templateHandler);
    }

    @DisplayName("staticHandler를 반환하는지 테스트")
    @ParameterizedTest
    @CsvSource({"/css/style.css", "/fonts/glyphicons-halflings-regular.woff", "/images/80-text.png", "/js/bootstrap.min.js"})
    void staticRequest(String uri) {
        HttpRequest request = new HttpRequestParser("GET " + uri + " HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Accept: text/css,*/*;q=0.1\n" +
                "Connection: keep-alive").parse();

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(StaticController.staticHandler);
    }

    @DisplayName("createUserHandler를 반환하는지 테스트")
    @Test
    void userCreateRequest() {
        HttpRequest request = new HttpRequestParser("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net").parse();

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(UserController.createUserHandler);
    }
}