package model;

import exception.utils.NoFileException;
import model.httpinfo.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HttpRequestTest {
    private static final String TEST_DIRECTORY = "./requests/";
    HttpRequest postRequest;
    HttpRequest getRequest;
    HttpRequest queryRequest;

    @BeforeEach
    public void setUp() throws IOException, NoFileException {
        postRequest = makeRequest("postRequest.txt");
        getRequest = makeRequest("getRequest.txt");
        queryRequest = makeRequest("queryRequest.txt");
    }

    private static HttpRequest makeRequest(String directory) throws IOException, NoFileException {
        byte[] message = FileIoUtils.loadFileFromClasspath(TEST_DIRECTORY + directory);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(message)));
        return new HttpRequest(bufferedReader, new Socket());
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
