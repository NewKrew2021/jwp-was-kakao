package webserver.http.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestParserTest {

    private final String resourceDirectory = "src/test/resources/";

    @Test
    @DisplayName("루트 주소로 Get 요청을 보냄")
    void isGetMethod() throws Exception {
        InputStream in = new FileInputStream(new File(resourceDirectory + "GET_ROOT_TEST"));
        HttpRequest httpRequest = HttpRequestParser.fromInputStream(in);
        assertThat(httpRequest.isMethod(HttpMethod.GET)).isTrue();
    }

    @Test
    @DisplayName("루트 주소로 POST 요청을 보냄")
    void isPostMethod() throws Exception {
        InputStream in = new FileInputStream(new File(resourceDirectory + "POST_ROOT_TEST"));
        HttpRequest httpRequest = HttpRequestParser.fromInputStream(in);
        assertThat(httpRequest.isMethod(HttpMethod.POST)).isTrue();
        assertThat(httpRequest.getParam("param1")).isEqualTo("paramValue1");
    }

    @Test
    @DisplayName("Post 전달")
    void postWithData() throws Exception {
        InputStream in = new FileInputStream(new File(resourceDirectory + "Http_POST"));
        HttpRequest request = HttpRequestParser.fromInputStream(in);

        assertEquals(true, request.isPostMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParam("userId"));
    }

    @Test
    @DisplayName("Post 전달 with QueryString 테스트")
    void postMethodWithQueryString() throws Exception {
        InputStream in = new FileInputStream(new File(resourceDirectory + "Http_POST2"));
        HttpRequest httpRequest = HttpRequestParser.fromInputStream(in);
        assertThat(httpRequest.isMethod(HttpMethod.POST)).isTrue();
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getParam("id")).isEqualTo("1");
        assertEquals("javajigi", httpRequest.getParam("userId"));
    }

}
