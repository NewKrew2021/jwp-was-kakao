package model;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HttpRequestTest {

    @Test
    public void parsePostRequest() throws IOException{
        String temp = "POST /api HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 33\n" +
                "Accept: */*\n" +
                "\r\n" +
                "userId=javajigi&password=password";

            InputStream is = new ByteArrayInputStream(temp.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        HttpRequest req = new HttpRequest(br);
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/api");
        assertThat(req.getProtocol()).isEqualTo("HTTP/1.1");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "localhost:8080");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "*/*");
        headerMap.put("Content-Length", "33");

        assertThat(req.getHeaderMap()).isEqualTo(headerMap);
        assertThat(req.getBody()).isEqualTo("userId=javajigi&password=password");

        Map<String, String> bodyExpected = new HashMap<>();
        bodyExpected.put("userId", "javajigi");
        bodyExpected.put("password", "password");
        assertThat(req.getParsedBody()).isEqualTo(bodyExpected);
    }

    @Test
    public void parseGetRequest() throws IOException{
        String temp = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";

        InputStream is = new ByteArrayInputStream(temp.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        HttpRequest req = new HttpRequest(br);
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/index.html");
        assertThat(req.getProtocol()).isEqualTo("HTTP/1.1");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "localhost:8080");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "*/*");

        assertThat(req.getHeaderMap()).isEqualTo(headerMap);
        assertThat(req.getBody()).isEqualTo("");
    }

    @Test
    public void parseQueryString() throws IOException{
        String temp = "GET /user/create?userId=jack&password=password&name=jackwon&email=jackwon%40kakaocorp.com HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";

        InputStream is = new ByteArrayInputStream(temp.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        HttpRequest req = new HttpRequest(br);

        Map<String, String> queryParameterMap = new HashMap<>();
        queryParameterMap.put("userId", "jack");
        queryParameterMap.put("password", "password");
        queryParameterMap.put("name", "jackwon");
        queryParameterMap.put("email", "jackwon%40kakaocorp.com");

        assertThat(req.getQueryParameterMap()).isEqualTo(queryParameterMap);
    }
}
