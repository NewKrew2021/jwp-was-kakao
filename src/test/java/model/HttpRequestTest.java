package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;

public class HttpRequestTest {
    HttpRequest postRequest;
    HttpRequest getRequest;
    HttpRequest queryRequest;

    @BeforeEach
    public void setUp() throws IOException{
        InputStream postMsg = new FileInputStream("./src/test/resources/postRequest");
        postRequest = makeRequest(postMsg);

        InputStream getMsg = new FileInputStream("./src/test/resources/getRequest");
        getRequest = makeRequest(getMsg);

        InputStream queryMsg = new FileInputStream("./src/test/resources/queryRequest");
        queryRequest = makeRequest(queryMsg);
    }

    private static HttpRequest makeRequest(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return new HttpRequest(br);
    }

    @Test
    public void getMethodTest() throws IOException {
        assertThat(postRequest.getMethod()).isEqualTo("POST");
        assertThat(getRequest.getMethod()).isEqualTo("GET");
    }


    @Test
    public void getPathTest() throws IOException {
        assertThat(postRequest.getPath()).isEqualTo("/api");
        assertThat(getRequest.getPath()).isEqualTo("/index.html");
    }

    @Test
    public void getProtocolTest() throws IOException {
        assertThat(postRequest.getProtocol()).isEqualTo("HTTP/1.1");
        assertThat(getRequest.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @Test
    public void headerMapTest() throws IOException{
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
    public void bodyTest() throws IOException {
        assertThat(postRequest.getBody()).isEqualTo("userId=javajigi&password=password");

        Map<String, String> bodyExpected = new HashMap<>();
        bodyExpected.put("userId", "javajigi");
        bodyExpected.put("password", "password");
        assertThat(postRequest.getParsedBody()).isEqualTo(bodyExpected);
    }

    @Test
    public void parseQueryString() throws IOException{
        Map<String, String> queryParameterMap = new HashMap<>();
        queryParameterMap.put("userId", "jack");
        queryParameterMap.put("password", "password");
        queryParameterMap.put("name", "jackwon");
        queryParameterMap.put("email", "jackwon%40kakaocorp.com");

        assertThat(queryRequest.getQueryParameterMap()).isEqualTo(queryParameterMap);
    }
}
