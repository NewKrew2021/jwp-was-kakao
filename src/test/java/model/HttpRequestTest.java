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
                "Accept: */*\n" +
                "\r\n" +
                "hello body";

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

            assertThat(req.getHeaderMap()).isEqualTo(headerMap);
            assertThat(req.getBody()).isEqualTo("hello body");
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
        assertThat(req.getBody()).isNull();
    }
}
