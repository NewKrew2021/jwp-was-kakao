package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HttpRequestTest {
    HttpRequest postRequest;
    HttpRequest getRequest;
    HttpRequest queryRequest;

    @BeforeEach
    public void setUp() throws IOException{
        String postMsg = "POST /api HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 33\n" +
                "Accept: */*\n" +
                "\r\n" +
                "userId=javajigi&password=password";
        postRequest = makeRequest(postMsg);

        String getMsg = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        getRequest = makeRequest(getMsg);

        String queryMsg = "GET /user/create?" +
                "userId=jack&password=password&name=jackwon&email=jackwon%40kakaocorp.com HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        queryRequest = makeRequest(queryMsg);
    }

    private static HttpRequest makeRequest(String httpMessage) throws IOException {
        InputStream is = new ByteArrayInputStream(httpMessage.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return new HttpRequest(br, new Socket());
    }

    @Test
    public void getMethodTest() {
        assertThat(postRequest.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(getRequest.getMethod()).isEqualTo(HttpMethod.GET);
    }


    @Test
    public void getPathTest() {
        assertThat(postRequest.getPath()).isEqualTo("/api");
        assertThat(getRequest.getPath()).isEqualTo("/index.html");
    }

    @Test
    public void getProtocolTest() {
        assertThat(postRequest.getProtocol()).isEqualTo("HTTP/1.1");
        assertThat(getRequest.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @Test
    public void headerMapTest() {
        Map<String, String> headerMap1 = new HashMap<>();
        headerMap1.put("Host", "localhost:8080");
        headerMap1.put("Connection", "keep-alive");
        headerMap1.put("Accept", "*/*");
        headerMap1.put("Content-Length", "33");
        assertThat(postRequest.getHeaderMap()).isEqualTo(headerMap1);

        Map<String, String> headerMap2 = new HashMap<>();
        headerMap2.put("Host", "localhost:8080");
        headerMap2.put("Connection", "keep-alive");
        headerMap2.put("Accept", "*/*");
        assertThat(getRequest.getHeaderMap()).isEqualTo(headerMap2);
    }

    @Test
    public void bodyTest() {
        assertThat(postRequest.getBody()).isEqualTo("userId=javajigi&password=password");

        Map<String, String> bodyExpected = new HashMap<>();
        bodyExpected.put("userId", "javajigi");
        bodyExpected.put("password", "password");
        assertThat(postRequest.getParsedBody()).isEqualTo(bodyExpected);
    }

    @Test
    public void parseQueryString() {
        Map<String, String> queryParameterMap = new HashMap<>();
        queryParameterMap.put("userId", "jack");
        queryParameterMap.put("password", "password");
        queryParameterMap.put("name", "jackwon");
        queryParameterMap.put("email", "jackwon%40kakaocorp.com");

        assertThat(queryRequest.getQueryParameterMap()).isEqualTo(queryParameterMap);
    }
}
