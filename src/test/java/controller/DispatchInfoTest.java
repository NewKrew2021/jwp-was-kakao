package controller;

import http.HttpRequest;
import http.HttpRequestParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DispatchInfoTest {

    @DisplayName("Template request일 경우 true")
    @Test
    void matchWithTemplateRequest() {
        DispatchInfo dispatchInfo = DispatchInfo.Template;
        HttpRequest request = new HttpRequestParser("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").parse();

        assertThat(dispatchInfo.matchWithTemplateRequest(request)).isTrue();
    }

    @DisplayName("Static request일 경우 true")
    @Test
    void matchWithStaticRequest() {
        DispatchInfo dispatchInfo = DispatchInfo.Static;
        HttpRequest request = new HttpRequestParser("GET /css/style.css HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Accept: text/css,*/*;q=0.1\n" +
                "Connection: keep-alive").parse();

        assertThat(dispatchInfo.matchWithStaticRequest(request)).isTrue();
    }

    @DisplayName("일치하는 request일 경우 true")
    @Test
    void matchWithRequest() {
        DispatchInfo dispatchInfo = DispatchInfo.UserCreate;
        HttpRequest request = new HttpRequestParser("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net").parse();

        assertThat(dispatchInfo.matchWithRequest(request)).isTrue();
    }

    @DisplayName("매치하는 request일 경우 true")
    @Test
    void matchWith() {
        DispatchInfo dispatchInfo = DispatchInfo.UserCreate;
        HttpRequest request = new HttpRequestParser("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net").parse();

        assertThat(dispatchInfo.matchWith(request)).isTrue();
    }
}