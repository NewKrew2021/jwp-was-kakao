package utils;

import controller.Handler;
import controller.StaticController;
import controller.TemplateController;
import controller.UserController;
import http.HttpRequest;
import http.HttpRequestParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherTest {
    private HttpRequestParser parser = new HttpRequestParser();

    @DisplayName("htmlHandler를 반환하는지 테스트")
    @Test
    void templateRequest() {
        parser.parse("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*");
        HttpRequest request = new HttpRequest(parser);

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(TemplateController.htmlHandler);
    }

    @DisplayName("cssHandler를 반환하는지 테스트")
    @Test
    void staticRequest() {
        parser.parse("GET /css/style.css HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Accept: text/css,*/*;q=0.1\n" +
                "Connection: keep-alive");
        HttpRequest request = new HttpRequest(parser);

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(StaticController.cssHandler);
    }

    @DisplayName("faviconHandler를 반환하는지 테스트")
    @Test
    void faviconRequest() {
        parser.parse("GET /favicon.ico HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Accept: text/css,*/*;q=0.1\n" +
                "Connection: keep-alive");
        HttpRequest request = new HttpRequest(parser);

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(TemplateController.faviconHandler);
    }

    @DisplayName("createUserHandler를 반환하는지 테스트")
    @Test
    void userCreateRequest() {
        parser.parse("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        HttpRequest request = new HttpRequest(parser);

        Handler handler = Dispatcher.findMatchingHandlers(request);

        assertThat(handler).isEqualTo(UserController.createUserHandler);
    }
}